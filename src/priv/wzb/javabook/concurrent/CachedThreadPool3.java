package priv.wzb.javabook.concurrent;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 14:35
 * @description:
 **/

public class CachedThreadPool3 {
	public static Integer extractResult(Future<Integer> f){
		try {
			return f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		List<CountingTask> tasks =
				IntStream.range(0, 10)
				.mapToObj(CountingTask::new)
				.collect(Collectors.toList());
		List<Future<Integer>> futures =
				exec.invokeAll(tasks);
		Integer sum = futures.stream()
				.map(CachedThreadPool3::extractResult)
				.reduce(0, Integer::sum);
		System.out.println("sum = " + sum);
		exec.shutdown();
	}
}
