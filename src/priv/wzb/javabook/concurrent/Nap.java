package priv.wzb.javabook.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 14:04
 * @description:
 **/

public class Nap {
	public Nap(double t) {
		try {
			TimeUnit.MILLISECONDS.sleep((int) (1000 * t));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Nap(double t, String msg) {
		this(t);
		System.out.println(msg);
	}
}
