package priv.wzb.javabook.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 17:04
 * @description:
 **/

public class GuardedIDField implements HasID {
	private static AtomicInteger counter = new AtomicInteger();
	private int id = counter.getAndIncrement();
	@Override
	public int getID(){
		return id;
	}

	public static void main(String[] args) {
		IDChecker.test(GuardedIDField::new);
	}
}
