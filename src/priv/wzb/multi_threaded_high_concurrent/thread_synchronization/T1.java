package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;

import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/25 20:43
 * @description:volatile线程可见性 加了volatile就是当缓冲区从jvm竹内润读取的值发生变化就进行提醒让其他线程重新读取
 */
public class T1 {
    //对比有无volatile
//    /*volatile*/ boolean running =true;
//    boolean running =true;
    volatile boolean running = true;
    void m(){
        System.out.println("m start");
        while (running){
//            System.out.println("1");
        }
        System.out.println("m end!");
    }
    public static void main(String[] args){
        T1 t = new T1();
        new Thread(t::m,"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        t.running = false;
    }
}
