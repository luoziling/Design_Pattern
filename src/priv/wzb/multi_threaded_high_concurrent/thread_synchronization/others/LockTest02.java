package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.others;

import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/9/7 19:25
 * @description:
 * 可重入锁
 */
public class LockTest02 {


    ReLock lock = new ReLock();

    public void a() throws InterruptedException {
        lock.lock();
        System.out.println(lock.getHoldCount());
        doSomeThing();
        lock.unlock();
        System.out.println(lock.getHoldCount());
    }

    //不可重入
    public void doSomeThing() throws InterruptedException {
        lock.lock();
        //...
        System.out.println(lock.getHoldCount());
        lock.unlock();
        System.out.println(lock.getHoldCount());

    }


    public static void main(String[] args) throws InterruptedException {
        LockTest02 test = new LockTest02();
        test.a();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(test.lock.getHoldCount());
    }

    class ReLock{
        //是否占用
        private boolean isLocked = false;
        private Thread lockedBy = null;//存储线程
        private int holdCount;
        //使用锁
        public synchronized void lock() throws InterruptedException {
            Thread t = Thread.currentThread();
            while (isLocked&&lockedBy!=t){

                wait();
            }

            isLocked = true;
            lockedBy = t;
            holdCount++;
        }
        //释放锁
        public synchronized void unlock(){
            if(Thread.currentThread() == lockedBy){
                holdCount--;
                if(holdCount==0){
                    isLocked = false;
                    notify();
                    lockedBy = null;
                }

            }

        }

        public int getHoldCount() {
            return holdCount;
        }
    }

}
