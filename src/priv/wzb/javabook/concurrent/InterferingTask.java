package priv.wzb.javabook.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 14:23
 * @description:
 **/

public class InterferingTask implements Runnable {
	final int id;
	private static volatile AtomicInteger val = new AtomicInteger(0);
	Lock myLock = new ReentrantLock(true);

	public InterferingTask(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		myLock.lock();
		try {
			for (int i = 0; i < 100; i++) {
				val.incrementAndGet();
			}
			System.out.println(id + " "+
					Thread.currentThread().getName() + " " + val);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			myLock.unlock();
		}
	}
}
