package priv.wzb.multi_threaded_high_concurrent.threadlocal.message;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * MultiTest
 *
 * @author yuzuki
 * @date 2021/7/11 16:57
 * @description: 多线程争抢同一个对象，并设置与获取看看是否回出现问题
 * 测试threadLocal是否能解决
 * 原子引用呢
 * @since 1.0.0
 */
public class MultAtomicTest {
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        // 100个线程，每个线程100次获取与设置
        // 共享资源
        NoticeStrategy strategy = new ConcreteStrategy1();
        AtomicReference<NoticeStrategy> atomicReference = new AtomicReference<>();
        atomicReference.set(strategy);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            Thread t = new Thread(()->{
                // 原子引用
                NoticeStrategy atomicStategy = atomicReference.get();
                // 尝试用ThreadLocal持有Strategy
                Thread thread = Thread.currentThread();
                String name = thread.getName();
                for (int j = 0; j < 100; j++) {
                    String s = name + "-value:" + finalI;
//                    atomicStategy.init(s);
//                    System.out.println("strategy.get() = " + strategy.get());
                    if (s != atomicStategy.uniform(s)){
                        System.out.println("===============================");
                        System.out.println("strategy.get() = " + atomicStategy.get());
                        System.out.println("===============================");
                        System.out.println("count.incrementAndGet() = " + count.incrementAndGet());
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
