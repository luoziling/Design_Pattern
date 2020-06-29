package priv.wzb.multi_threaded_high_concurrent.cyclic_barrier;

import java.util.concurrent.CyclicBarrier;

/**
 * @author Satsuki
 * @time 2020/5/25 13:53
 * @description:
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            final int tempInt = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t 收集到第" + tempInt + "龙珠");
                try {
                    cyclicBarrier.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
