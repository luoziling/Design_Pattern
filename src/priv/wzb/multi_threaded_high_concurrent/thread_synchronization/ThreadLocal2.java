package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/29 16:56
 * @description:
 */
public class ThreadLocal2 {
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();

    }

    static class Person{
        String name="zhangsan";
}
}
