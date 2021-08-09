package priv.wzb.redis.stream.compensate;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import priv.wzb.redis.stream.NoticeMediator;
import priv.wzb.redis.stream.config.RedisStreamConfig;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program:
 * @author: yuzuki
 * @create: 2021-04-15 18:17
 * @description: stream 处理pending list的补偿任务
 **/
@Component
@Slf4j
public class StreamCompensateTask {
	@Resource(name = "noticeStringRedisTemplate")
	StringRedisTemplate stringRedisTemplate;
	@Resource
	RedisStreamConfig redisStreamConfig;

	@Resource
	private NoticeMediator noticeMediator;

	@Value("${base.name}")
	private String systemName;

	@Value("${mail.open}")
	private boolean open;

	/**
	 * 获取当前环境
	 */
	@Value("${spring.profiles.active}")
	private String profiles;
	private static final String BR = "<br />";

	public static final int LIMIT_SIZE = 3;
	public static int count = 0;
	@Value("${stream.queueSize}")
	private int streamLimitSize;


	/**
	 * 距离上次交付后的处理时间超过一小时还未被确认则认为需要补偿处理
	 * 单位：秒
	 * 假设10分钟还未发出通知需要补偿，可更改
	 */
	private static final int HANDLE_TIME_OUT = 10 * 60;
//	private static final int HANDLE_TIME_OUT = 1;

	@Scheduled(cron = "0 0/10 * * * ?")
	@Async
	public void pendingHandler() {

		List<PendingMessage> pendingMessageList = getPendingMessagesByMultiConsumer();
		// 重复处理三次pending list
		for (int i = 0; i < LIMIT_SIZE; i++) {
			if (!CollUtil.isEmpty(pendingMessageList)) {
				for (PendingMessage pendingMessage : pendingMessageList) {
					try {
						// 消息消费
						log.info("pendingMessage = " + pendingMessage);
						// 根据消息ID查询消息详情
						RecordId messageId = pendingMessage.getId();
						Range<String> range = Range
								.from(Range.Bound.inclusive(messageId.toString()))
								.to(Range.Bound.inclusive(messageId.toString()));
						List<MapRecord<String, String, String>> mapRecordList = stringRedisTemplate.<String, String>opsForStream().range(redisStreamConfig.getStream(), range);
						if (CollUtil.isEmpty(mapRecordList)) {
							// 找不到消息实例则跳过
							continue;
						}
						// 不为空则获取第一条消息
						Map<String, String> map = mapRecordList.get(0).getValue();
						log.info("MessageMap = " + map);
//						// 发送通知
						noticeMediator.sendNotice(map);

						// 消费确认
						stringRedisTemplate.opsForStream().acknowledge(redisStreamConfig.getStream(), redisStreamConfig.getConsumerGroup(), pendingMessage.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 重新获取pending list
//			pendingMessageList = getPendingMessages(notifier);
			pendingMessageList = getPendingMessagesByMultiConsumer();
		}
		// 如果补偿处理后还有问题则发送邮件通知
		if (CollUtil.isNotEmpty(pendingMessageList)) {
			// 发送邮件
			if (open && "prod".equals(this.profiles)) {
				StringWriter sw = new StringWriter();
				sw.append("异常系统：").append(systemName).append(BR);
				sw.append("异常环境：").append(this.profiles).append(BR);
				sw.append("异常时间：").append(DateTime.now().toString()).append(BR);
				sw.append("消息补偿措施异常通知信息如下: ").append(String.valueOf(pendingMessageList)).append(BR);
				//发送邮件
			}
			log.info("补偿任务运行，仍存在多次执行失败的消息：{}",pendingMessageList);
			// 多次失败后打印日志并丢弃消息
			for (PendingMessage pendingMessage : pendingMessageList) {
				stringRedisTemplate.opsForStream().acknowledge(redisStreamConfig.getStream(), redisStreamConfig.getConsumerGroup(), pendingMessage.getId());
			}
		}

		// 容量大小判断
		Long size = stringRedisTemplate.opsForStream().size(redisStreamConfig.getStream());
		if (Objects.nonNull(size) && size > streamLimitSize) {
			// 缩容
			stringRedisTemplate.opsForStream().trim(redisStreamConfig.getStream(), streamLimitSize / 2);
		}

	}

	/**
	 * 多消费者下获取pendingMessage
	 * @return 多消费者未消费的pendingList
	 */
	@NotNull
	private List<PendingMessage> getPendingMessagesByMultiConsumer() {
		List<PendingMessage> pendingMessageList = new ArrayList<>();

		// 根据固定配置构造consumer，方便后续根据消费者身份确定待消费的消息
		// 实现优先只开启一个consumer去消费产生的message，后续如有扩容需要可开启多个pendingHandler 并指定不同consumer
		// 获取所有consumer下的pendingList并统一消费
		List<String> consumers = redisStreamConfig.getConsumers();
		for (String consumer : consumers) {
			Consumer notifier = Consumer.from(redisStreamConfig.getConsumerGroup(), consumer);
			pendingMessageList.addAll(getPendingMessages(notifier));
		}
		return pendingMessageList;
	}

	/**
	 * 获取待处理消息队列（之前被处理过但由于种种原因没有ACK的）
	 * 控制定时任务的执行时间尽量在每天一次最好凌晨执行
	 * 但由于redis的单线程机制，是否可避免并发问题
	 * 测试，用户消费某个消息，进入等待状态，此时补偿机制启动是否会出现重复消费现象
	 * 可通过待处理队列的IDLE （已读取时长）属性来降低重复消费的可能
	 *
	 * @param notifier 消费者
	 * @return 返回指定消费者 处理超时的pendingMessageList
	 */
	private List<PendingMessage> getPendingMessages(Consumer notifier) {
		PendingMessages pendingMessages = stringRedisTemplate.opsForStream().pending(redisStreamConfig.getStream(), notifier);
		List<PendingMessage> pendingMessageList = new ArrayList<>();
		pendingMessages.forEach(e -> {
			// 超过一小时才认为超时需要补偿执行，否则容易出现重复消费现象。
			if (e.getElapsedTimeSinceLastDelivery().getSeconds() > HANDLE_TIME_OUT) {
				pendingMessageList.add(e);
			}
		});
		return pendingMessageList;
	}

	/**
	 * user_id 首字母转大写
	 *
	 * @param str
	 * @return
	 */
	public String upperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
