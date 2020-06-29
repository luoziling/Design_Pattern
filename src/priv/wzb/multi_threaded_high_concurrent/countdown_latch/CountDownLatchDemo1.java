package priv.wzb.multi_threaded_high_concurrent.countdown_latch;

import java.util.concurrent.CountDownLatch;

/**
 * @author Satsuki
 * @time 2020/5/25 13:30
 * @description:
 */
public class CountDownLatchDemo1 {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t国，被灭");
                countDownLatch.countDown();
            },CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t   国，一桶浆糊");


    }
}
