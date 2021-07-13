package priv.wzb.multi_threaded_high_concurrent.volatile1;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-10-28 10:35
 * @description:
 **/

public class ReorderingTest {
	public static void main(String[] args) {
		AtomicInteger context = new AtomicInteger(0);
		AtomicBoolean flag = new AtomicBoolean(false);
		ExecutorService service = new ThreadPoolExecutor(
				2,
				2,
				60,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(),
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r);
					}
				});
		service.execute(()->{
			context.set(1);
			flag.set(true);
		});
		service.execute(()->{
			if (flag.get()){
				System.out.println("context.get() = " + context.get());
			}
			System.out.println("context.get() = " + context.get());
		});

	}
}
