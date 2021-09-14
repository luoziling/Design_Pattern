package priv.wzb.multi_threaded_high_concurrent.future;

import com.google.common.util.concurrent.*;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FutureListener;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-09-13 09:38
 * @description:
 **/

public class FutureTest {
	@Test
	public void FutureDemo1() throws ExecutionException, InterruptedException {
		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Integer> future = executor.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				simulation();
				return 100;
			}
		});
		// future 阻塞+等待
		System.out.println("future.get() = " + future.get());
		System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
	}

	public static void simulation(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void futureWithNetty() throws InterruptedException {
		long start = System.currentTimeMillis();
		EventExecutorGroup group = new DefaultEventExecutorGroup(4);
		io.netty.util.concurrent.Future<Integer> nettyFuture = group.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("FutureTest.call");
				simulation();
				return 100;
			}
		});
		nettyFuture.addListener(new FutureListener<Object>(){
			@Override
			public void operationComplete(io.netty.util.concurrent.Future<Object> future) throws Exception {
				System.out.println("future.get() = " + future.get());
			}
		});
		System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
		new CountDownLatch(1).await();
	}

	@Test
	public void guavaCallback() throws InterruptedException {
		long start = System.currentTimeMillis();
		ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
		ListenableFuture<Integer> future = service.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("FutureTest.call");
				simulation();
				return 100;
			}
		});
		Futures.addCallback(future, new FutureCallback<Integer>() {
			@Override
			public void onSuccess(Integer integer) {
				System.out.println("FutureTest.onSuccess");
				System.out.println("integer = " + integer);
			}

			@Override
			public void onFailure(Throwable throwable) {
				System.out.println("FutureTest.onFailure");
				System.out.println("throwable.getMessage() = " + throwable.getMessage());
			}
		});
		System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
		new CountDownLatch(1).await();
	}

	@Test
	public void completableFutureTest() throws InterruptedException {
		long start = System.currentTimeMillis();
		CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
			System.out.println("FutureTest.completableFutureTest");
			simulation();
			return 100;
		});
		completableFuture.whenComplete((result,e)->{
			System.out.println("result = " + result);
		});
		System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
		new CountDownLatch(1).await();
	}

	@Test
	public void callbackHell() throws InterruptedException {
		long start = System.currentTimeMillis();
		CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
			System.out.println("FutureTest.completableFutureTest  111");
			simulation();
			return 100;
		});
		completableFuture = completableFuture.thenCompose(e -> {
			return CompletableFuture.supplyAsync(() -> {
				System.out.println("回调的回调");
				simulation();
				return e + 100;
			});
		});
		completableFuture.whenComplete((result,e)->{
			System.out.println("result = " + result);
		});
		System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
		new CountDownLatch(1).await();
	}
}
