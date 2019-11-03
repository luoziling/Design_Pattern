package priv.wzb.multi_threaded_high_concurrent.thread_reentrantlock;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Satsuki
 * @time 2019/10/21 17:54
 * @description:
 */
public class CyclicTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CyclicBarrier barrier = new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                System.out.println("好了，大家可以去吃饭了……"  );
//                ReentrantLock
            }
        });

        // 尝试替换成CountDownLatch 可以替换为CountDownLatch
        CountDownLatch countDownLatch = new CountDownLatch(4);
        System.out.println("要吃饭，必须所有人都到终点，oK?");
        System.out.println("不放弃不抛弃！");
        for (int i = 0; i < 4; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":Go");
                    try {
                        TimeUnit.MILLISECONDS.sleep((long)(2000*Math.random()));
//                        countDownLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+ ":我到终点了");
                    try {
//                        countDownLatch.await();
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName()+ ":终于可以吃饭啦！");

                }
            });
        }
    }


}
