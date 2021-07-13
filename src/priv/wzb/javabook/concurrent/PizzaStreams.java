package priv.wzb.javabook.concurrent;

import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 17:33
 * @description:
 **/

public class PizzaStreams {
	static final int QUANTITY = 5;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		IntStream.range(0,QUANTITY)
				.mapToObj(Pizza::new)
				.parallel()
				.forEach(za->{
					while (!za.complete()){
						za.next();
					}
				});
		System.out.println();
		System.out.println("消耗时间:" + (System.currentTimeMillis()-start));
	}
}
