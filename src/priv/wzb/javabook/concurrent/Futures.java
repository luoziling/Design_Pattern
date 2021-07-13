package priv.wzb.javabook.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 14:43
 * @description:
 **/

public class Futures {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		Future<Integer> f =
				exec.submit(new CountingTask(99));
		System.out.println(f.get());
		exec.shutdown();
	}
}
