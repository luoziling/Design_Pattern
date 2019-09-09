package priv.wzb.multi_threaded_high_concurrent.thread_reentrantlock;

import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/27 23:23
 * @description:ReentrantLock可以完成synchronized的功能所以可以替代synchronized
 */
public class ReentrantLock1 {
    synchronized void m1(){
        for(int i =0;i<10;i++){
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }
    synchronized void m2(){
        System.out.println("m2...");
    }

    public static void main(String[] args){
        ReentrantLock1 r1 = new ReentrantLock1();
        new Thread(r1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        new Thread(r1::m2).start();
    }
}
