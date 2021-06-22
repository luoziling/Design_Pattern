package priv.wzb.redis.stream;

import java.util.List;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-06-02 10:59
 * @description: consumer group数据模型
 **/

public class ConsumerGroup {

	/**
	 * 消费者组名称
	 */
	private String groupName;
	/**
	 * 消费者组中包含的消费者
	 */
	private List<Consumer> consumers;

}
