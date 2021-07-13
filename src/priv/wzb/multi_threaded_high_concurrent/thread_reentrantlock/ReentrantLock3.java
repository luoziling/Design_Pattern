package priv.wzb.multi_threaded_high_concurrent.thread_reentrantlock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Satsuki
 * @time 2019/5/27 23:34
 * @description:可以通过interrupt来打断
 */
public class ReentrantLock3 {
//    public ReentrantLock void test(){
//        System.out.println("aaa");
//    }
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1.start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                System.out.println("interrupted");
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();//打断线程2的等待

    }
}
