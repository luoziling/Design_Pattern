package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.others;

/**
 * @author Satsuki
 * @time 2019/9/7 19:25
 * @description:
 * 可重入锁
 * 锁可以延续使用
 */
public class LockTest {
    public void test(){
        synchronized (this){
            while (true){
                synchronized (this){
                    System.out.println("ReentrantLock!");
                }
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        new LockTest().test();
    }
}
