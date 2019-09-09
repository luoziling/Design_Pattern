package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;

import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/25 20:27
 * @description:synchronized抛出异常释放锁
 */
public class T {
    int count = 0;
    synchronized void m(){
        System.out.println(Thread.currentThread().getName() + "start");
        while (true){
            count++;
            System.out.println(Thread.currentThread().getName() + "count="+count);
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(count == 5){
                //此处抛出异常，锁将释放，
                // 要想不被释放可以在这里进行catch，然后让循环继续
                int i = 1/0;
            }
        }
    }
    public static void main(String[] args){
        T t = new T();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                t.m();
            }
        };
        new Thread(r,"t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        new Thread(r,"t2").start();
//        try {
//            new Thread()->t.m();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        new Thread(t::m,"t1").start();

    }
}
