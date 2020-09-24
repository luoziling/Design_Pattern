package priv.wzb.javabook.concurrent;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 16:54
 * @description:
 **/

public class IDChecker {
	public static final int SIZE = 100_000;

	static class MakeObjects implements Supplier<List<Integer>>{
		private Supplier<HasID> gen;

		public MakeObjects(Supplier<HasID> gen) {
			this.gen = gen;
		}

		@Override
		public List<Integer> get() {
			return Stream.generate(gen)
					.limit(SIZE)
					.map(HasID::getID)
					.collect(Collectors.toList());
		}


	}

	public static void test(Supplier<HasID> gen){
		CompletableFuture<List<Integer>> groupA =
				CompletableFuture.supplyAsync(new MakeObjects(gen)),
				groupB =
						CompletableFuture.supplyAsync(new MakeObjects(gen));
		groupA.thenAcceptBoth(groupB,(a,b)->{
			System.out.println(
					Sets.intersection(
							Sets.newHashSet(a),
							Sets.newHashSet(b)
					).size()
			);
		}).join();
	}

	public static void main(String[] args) {
		IDChecker.test(StaticIDField::new);
	}
}
