package priv.wzb.multi_threaded_high_concurrent.thread_reentrantlock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/10/21 17:33
 * @description:
 */
//    CountDownLatch
//    CyclicBarrier
public class CountAndCyclic {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) {
        CountAndCyclic countAndCyclic = new CountAndCyclic();
        System.out.println("主线程初始化完成开始执行...");
        new Thread(()->{
            System.out.println("subClassA start working...");
            try {
                TimeUnit.SECONDS.sleep(2);
                // 此线程执行完毕计数-1
                countAndCyclic.countDownLatch.countDown();
                System.out.println("subClassA end working...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            // 阻塞主线程等待子线程执行完毕
            countAndCyclic.countDownLatch.await();
            System.out.println("主线程继续执行.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

//    class subClassA implements Runnable{
//        @Override
//        public void run() {
//
//        }
//    }
}
