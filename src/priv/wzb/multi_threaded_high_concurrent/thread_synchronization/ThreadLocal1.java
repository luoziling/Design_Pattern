package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;

import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/29 16:53
 * @description:
 */
public class ThreadLocal1 {
    volatile static Person p = new Person();
    public static void main(String[] args){
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(p.name);
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            p.name = "bb";
        }).start();
    }
}
class Person{
    String name="aa";
}
