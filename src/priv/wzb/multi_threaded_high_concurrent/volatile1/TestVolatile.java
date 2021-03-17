package priv.wzb.multi_threaded_high_concurrent.volatile1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Satsuki
 * @time 2020/5/18 12:42
 * @description:
 */
public class TestVolatile {
//     int num = 0;
    volatile int num = 0;
    // 使用Atomic数据类型保证++操作的原子性
    AtomicInteger numAtom = new AtomicInteger();

    public void addTo60(){
        num = 60;
    }
    public void addPlus(){
//        synchronized (this){
//            num++;
//        }
        num++;
        numAtom.incrementAndGet();

    }

    public static void main(String[] args) {
        // 验证可见性
        TestVolatile testVolatile = new TestVolatile();

//        new Thread(()->{
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            testVolatile.addTo60();
//            System.out.println(Thread.currentThread().getName() + ":" + testVolatile.num);
//
//
//        },"AAA").start();
//
//        // 不加volatile不可见其他多线程的改动（不会主动去内存中刷新当前线程本地内存中存储的值
//        while (testVolatile.num==0){
//
//        }
//        System.out.println(Thread.currentThread().getName() + "over");

//        // 不保证原子性
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    testVolatile.addPlus();
                }
            },String.valueOf(i)).start();
        }
        // 保证线程执行完毕
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(testVolatile.num);
        System.out.println(Thread.currentThread().getName() + ":" + testVolatile.numAtom);
    }
}
