package priv.wzb.javabook.concurrent;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 14:03
 * @description:
 **/

public class NapTask implements Runnable {
	final int id;

	public NapTask(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		new Nap(0.1);
		System.out.println(this + " " +Thread.currentThread().getName());
	}

	@Override
	public String toString() {
		return "NapTask[" + id + "]";
	}
}
