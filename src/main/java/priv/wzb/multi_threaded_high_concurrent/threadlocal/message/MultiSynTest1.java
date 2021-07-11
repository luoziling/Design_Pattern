package priv.wzb.multi_threaded_high_concurrent.threadlocal.message;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * MultiTest
 *
 * @author yuzuki
 * @date 2021/7/11 16:57
 * @description: 多线程争抢同一个对象，并设置与获取看看是否回出现问题
 * 测试threadLocal是否能解决
 * @since 1.0.0
 */
public class MultiSynTest1 {
    private static AtomicInteger count = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        // 100个线程，每个线程100次获取与设置
        // 共享资源
        NoticeStrategy strategy = new ConcreteStrategy1();

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            Thread t = new Thread(()->{
                // 尝试用ThreadLocal持有Strategy
                Thread thread = Thread.currentThread();
                String name = thread.getName();
                // 锁定共享资源，避免多线程问题

                for (int j = 0; j < 100; j++) {
                    String s = name + "-value:" + finalI;
                    synchronized (strategy){
                        strategy.init(s);
//                    System.out.println("strategy.get() = " + strategy.get());
                        if (s != strategy.get()){
                            System.out.println("===============================");
                            System.out.println("strategy.get() = " + strategy.get());
                            System.out.println("===============================");
                            System.out.println("count.incrementAndGet() = " + count.incrementAndGet());
                        }
                    }

                }


            },"Thread: " + i);
            t.start();
//            t.join();
        }
        Thread.sleep(5000);
        System.out.println("count = " + count);

    }
}
