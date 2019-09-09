package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;


/**
 * @author Satsuki
 * @time 2019/7/29 14:06
 * @description:
 */
public class Test_01 {
    private int count = 0;
    private Object o = new Object();

    //锁临界资源对象
    public void testSync1(){
        synchronized(o){
            System.out.println(Thread.currentThread().getName() + "count=" + count++);
        }
    }

    //this和synchronized 都是锁当前对象
    public void testSync2(){
        synchronized (this){
            System.out.println(Thread.currentThread().getName() + "count=" + count++);
        }

    }

    public synchronized void testSync3(){
        System.out.println(Thread.currentThread().getName() + "count=" + count++);
    }
}
