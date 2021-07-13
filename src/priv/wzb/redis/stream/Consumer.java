package priv.wzb.redis.stream;

import java.util.List;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-06-02 11:00
 * @description: 真实消费者，依附consumer group
 **/

public class Consumer {
	/**
	 * 消费者名称
	 */
	private String name;
	/**
	 * 被consumer读取但未被
	 */
	private List<Integer> pendingIds;
}
