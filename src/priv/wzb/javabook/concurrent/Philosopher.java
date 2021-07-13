package priv.wzb.javabook.concurrent;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 13:46
 * @description:
 **/

public class Philosopher implements Runnable {
	private final int seat;
	private final StickHolder left,right;

	public Philosopher(int seat, StickHolder left, StickHolder right) {
		this.seat = seat;
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return "p" + seat;
	}

	@Override
	public void run() {
		while (true){
			System.out.println("Thinking");
			right.pickUp();
			left.pickUp();
			System.out.println(this + "eating");
			right.putDown();
			left.putDown();
		}
	}
}
