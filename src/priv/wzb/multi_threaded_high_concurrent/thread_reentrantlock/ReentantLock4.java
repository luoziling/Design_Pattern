package priv.wzb.multi_threaded_high_concurrent.thread_reentrantlock;

import java.util.concurrent.locks.ReentrantLock;


/**
 * @author Satsuki
 * @time 2019/5/27 23:40
 * @description:ReentrantLock可以指定为公平锁，
 * 公平锁就是哪个线程等待时间长就哪个线程先执行
 * ReentrantLock必须要在finally中unlock(), 否则,如果在被加锁的代码中抛出了异常,那么这个锁将会永远无法释放.
 */
public class ReentantLock4 extends Thread {
    private static ReentrantLock lock = new ReentrantLock(true);
    public void run(){
        for(int i = 0 ;i<100;i++){
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "获得锁");
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args){
        ReentantLock4 r4 = new ReentantLock4();
        Thread th1 = new Thread(r4);
        Thread th2 = new Thread(r4);
        th1.start();
        th2.start();

    }
}
