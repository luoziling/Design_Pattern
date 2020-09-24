package priv.wzb.javabook.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 14:07
 * @description:
 **/

public class SingleThreadExecutor {
	public static void main(String[] args) {
		ExecutorService exec =
				Executors.newSingleThreadExecutor();
		IntStream.range(0, 10)
				.mapToObj(NapTask::new)
				.forEach(exec::execute);
		System.out.println("All tasks submitted");
		exec.shutdown();
		// 未结束所有任务就等待
		while (!exec.isTerminated()){
			System.out.println(Thread.currentThread().getName()
			+" awaiting termination");
			new Nap(0.1);
		}
	}
}
