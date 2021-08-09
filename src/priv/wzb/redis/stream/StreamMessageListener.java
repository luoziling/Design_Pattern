package priv.wzb.redis.stream;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import priv.wzb.redis.stream.config.RedisStreamConfig;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: redis
 * @author: wangzibai01
 * @create: 2021-04-12 15:47
 * @description:
 **/
@Component
@Slf4j
public class StreamMessageListener implements StreamListener<String, MapRecord<String, String, String>> {



	@Resource
	RedisStreamConfig redisStreamConfig;

	@Resource(name = "noticeStringRedisTemplate")
	StringRedisTemplate stringRedisTemplate;


	@Resource
	private NoticeMediator noticeMediator;

	public static int count = 0;

	@Value("${mail.open}")
	private boolean open;

	/**
	 * 获取当前环境
	 */
	@Value("${spring.profiles.active}")
	private String profiles;
	private static final String BR = "<br />";
	private static final String PROD_ENVIRONMENT = "prod";

	@Override
	public void onMessage(MapRecord<String, String, String> message) {
		log.info("message:{}", message);
		Map<String, String> body = null;

		try {
			// 消息ID
			RecordId messageId = message.getId();

			// 消息的key和value
			body = message.getValue();

			log.info("stream message。messageId={}, stream={}, body={}", messageId, message.getStream(), body);
//			// 发送通知


			// 测试补偿机制，不消费
			this.stringRedisTemplate.opsForStream().acknowledge(redisStreamConfig.getConsumerGroup(), message);
		} catch (Exception e) {
			log.info("open = {}",open);
			// 发送邮件通知
			// 考虑只在生产环境发送通知
			if (open && PROD_ENVIRONMENT.equals(this.profiles)){
			}

			e.printStackTrace();
		}

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
