package priv.wzb.javabook.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 15:43
 * @description:
 **/

public class CompletedMachina {
	public static void main(String[] args) {
		CompletableFuture<Machina> cf =
				CompletableFuture.completedFuture(
						new Machina(0)
				);
		try {
			Machina m = cf.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
