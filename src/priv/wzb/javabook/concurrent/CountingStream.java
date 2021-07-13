package priv.wzb.javabook.concurrent;

import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 14:53
 * @description:
 **/

public class CountingStream {
	public static void main(String[] args) {
		System.out.println(
				IntStream.range(0, 10)
				.parallel()
				.mapToObj(CountingTask::new)
				.mapToInt(ct -> {
					try {
						return ct.call();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return -1;
				})
				.reduce(0,Integer::sum)
		);
	}
}
