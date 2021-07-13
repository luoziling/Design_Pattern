package priv.wzb.javabook.concurrent;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 17:29
 * @description:
 **/

public class OnePizza {
	public static void main(String[] args) {
		Pizza za = new Pizza(0);
		long start = System.currentTimeMillis();
		while (!za.complete()){
			za.next();
		}
		System.out.println("消耗时间:" + (System.currentTimeMillis()-start));
	}
}
