package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.others;

/**
 * @author Satsuki
 * @time 2019/9/7 19:25
 * @description:
 * 不可重入锁
 */
public class LockTest01 {


    Lock lock = new Lock();

    public void a() throws InterruptedException {
        lock.lock();
        doSomeThing();
        lock.unlock();
    }

    //不可重入
    public void doSomeThing() throws InterruptedException {
        lock.lock();
        //...
        lock.unlock();

    }


    public static void main(String[] args) throws InterruptedException {
        LockTest01 test = new LockTest01();
        test.a();
        test.doSomeThing();
    }

    class Lock{
        //是否占用
        private boolean isLocked = false;
        //使用锁
        public synchronized void lock() throws InterruptedException {
            while (isLocked){
                wait();
            }

            isLocked = true;
        }
        //释放锁
        public synchronized void unlock(){
            isLocked = false;
            notify();
        }
    }

}
