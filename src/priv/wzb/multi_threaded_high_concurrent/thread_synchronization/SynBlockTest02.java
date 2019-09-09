package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/9/6 17:27
 * @description:
 */
public class SynBlockTest02 {
    public static void main(String[] args) {

        CopyOnWriteArrayList<String> list  = new CopyOnWriteArrayList<>();

//        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
//                synchronized (list){
//                    list.add(Thread.currentThread().getName());
//                }
                list.add(Thread.currentThread().getName());

            }).start();

        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }
}
