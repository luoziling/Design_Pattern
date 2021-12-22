package priv.wzb.multi_threaded_high_concurrent.atomic_test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Satsuki
 * @time 2020/5/20 12:48
 * @description: 解决ABA问题 AtomicStampedReference
 */
public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    // 使用AtomicStampedReference加上版本号限制ABA问题
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        // ABA问题的演示
        new Thread(()->{
            // 在t1线程中更改了两次改过又改回100
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();


        new Thread(()->{
            // 防止线程执行混乱
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 在t2线程中只根据是否是100进行判定和更改
            System.out.println(atomicReference.compareAndSet(100,2020) + "\t" + atomicReference.get());
        },"t2").start();

        // ABA问题的解决：
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第一次版本号：" + stamp);
            try {
                // 停一会 使得t3 t4都能拿到版本号
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 更改（包括更新版本号
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t第2次版本号：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t第3次版本号：" + atomicStampedReference.getStamp());

        },"t3").start();


        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第一次版本号：" + stamp);
            try {
                // 停一会
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean res = atomicStampedReference.compareAndSet(100, 2020, stamp, stamp + 1);

            System.out.println(Thread.currentThread().getName() + "\t第4次版本号：" + atomicStampedReference.getStamp() + "是否成功：" + res);

            System.out.println(Thread.currentThread().getName() + "\t当前的最新值：" + atomicStampedReference.getReference());
        },"t4").start();
        Thread t11 = new Thread(() -> {
        });
        t11.interrupt();
        LockSupport.park();
    }
}
