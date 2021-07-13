package priv.wzb.javabook.concurrent;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 13:48
 * @description:
 **/

public class DiningPhilosophers {
	private StickHolder[] sticks;
	private Philosopher[] philosophers;

	/**
	 * 初始化n支筷子n个哲学家
	 * 每个哲学家分配可拿的两根筷子
	 * @param n
	 */
	public DiningPhilosophers(int n){
		sticks = new StickHolder[n];
		// public static <T> void setAll(T[] array, IntFunction<? extends T> generator)
		Arrays.setAll(sticks, i-> new StickHolder());
		philosophers = new Philosopher[n];
		Arrays.setAll(philosophers,i->
				new Philosopher(i,sticks[i],sticks[(i+1)%n]));
		// Fix by reversing stick order for this one:
		// 设置第二个哲学家先左后右，破坏循环等待条件
		 philosophers[1] =                     // [2]
		   new Philosopher(0, sticks[0], sticks[1]);
		Arrays.stream(philosophers)
				.forEach(CompletableFuture::runAsync);
	}

	public static void main(String[] args) {
		new DiningPhilosophers(5);
		new Nap(3,"shutdown");
	}
}
