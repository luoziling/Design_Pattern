package priv.wzb.multi_threaded_high_concurrent.countdown_latch;

import java.util.concurrent.CountDownLatch;

/**
 * @author Satsuki
 * @time 2020/5/25 13:30
 * @description:
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t上完自习，离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t   main最后离开教室，释放资源");


    }
}
