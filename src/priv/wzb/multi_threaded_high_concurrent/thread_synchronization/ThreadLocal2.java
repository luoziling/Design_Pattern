package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;

import java.sql.Time;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/29 16:56
 * @description:
 */
public class ThreadLocal2 {
    // ThreadLocal就像每个线程的私有内存空间一样，其他线程无法访问
    static ThreadLocal<Person> tl = new ThreadLocal<>();
//    CopyOnWriteArrayList

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
