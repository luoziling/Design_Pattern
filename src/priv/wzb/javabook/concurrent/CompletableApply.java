package priv.wzb.javabook.concurrent;

import java.util.concurrent.CompletableFuture;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 15:46
 * @description:
 **/

public class CompletableApply {
	public static void main(String[] args) {
		CompletableFuture<Machina>cf =
				CompletableFuture.completedFuture(new Machina(0));
		CompletableFuture<Machina> cf2 =
				cf.thenApply(Machina::work);
		CompletableFuture<Machina> cf3 =
				cf2.thenApply(Machina::work);
		CompletableFuture<Machina> cf4 =
				cf3.thenApply(Machina::work);
		CompletableFuture<Machina> cf5 =
				cf4.thenApply(Machina::work);
	}
}
