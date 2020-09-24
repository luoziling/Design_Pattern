package priv.wzb.javabook.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 14:18
 * @description:
 **/

public class CachedThreadPool {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		IntStream.range(0,10)
				.mapToObj(NapTask::new)
				.forEach(exec::execute);
		exec.shutdown();
	}
}
