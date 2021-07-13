package priv.wzb.javabook.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 15:21
 * @description:
 **/

public class QuittableTask implements Runnable {
	final int id;

	public QuittableTask(int id) {
		this.id = id;
	}

	private AtomicBoolean running = new AtomicBoolean(true);
	public void quit(){
		running.set(false);
	}

	@Override
	public void run() {
		while (running.get()){
			new Nap(0.1);
		}
		System.out.println(id + " ");
	}
}
