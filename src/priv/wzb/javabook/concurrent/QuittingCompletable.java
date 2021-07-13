package priv.wzb.javabook.concurrent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 15:32
 * @description:
 **/

public class QuittingCompletable {
	public static void main(String[] args) {
		List<QuittableTask> tasks =
				IntStream.range(1,QuittingTasks.COUNT)
				.mapToObj(QuittableTask::new)
				.collect(Collectors.toList());
		List<CompletableFuture<Void>> cfutures =
				tasks.stream()
				.map(CompletableFuture::runAsync)
				.collect(Collectors.toList());
		new Nap(1);
		tasks.forEach(QuittableTask::quit);
		cfutures.forEach(CompletableFuture::join);
	}
}
