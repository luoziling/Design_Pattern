package priv.wzb.javabook.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 13:38
 * @description:
 **/

public class StickHolder {
	private static class Chopstick{}
	private Chopstick stick = new Chopstick();
	private BlockingQueue<Chopstick> holder =
			new ArrayBlockingQueue<>(1);

	public StickHolder(){
		putDown();
	}

	public void pickUp(){
		try {
			// Blocks if unavailable
			holder.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void putDown(){
		try {
			holder.put(stick);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
