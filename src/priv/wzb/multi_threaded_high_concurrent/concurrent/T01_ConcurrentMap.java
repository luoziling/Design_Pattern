package priv.wzb.multi_threaded_high_concurrent.concurrent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author Satsuki
 * @time 2019/5/30 0:30
 * @description:
 */
public class T01_ConcurrentMap {
    public static void main(String[] args) {
//        Map<String,String> map = new ConcurrentHashMap<>();//效率高锁的细化
//        Map<String,String> map = new ConcurrentSkipListMap<>();
        Map<String,String> map = new Hashtable<>();
//        Map<String,String> map = new HashMap<>();
        //treeMap
        Random r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ths.length);
        long start = System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    map.put("a" + r.nextInt(100000),"a" + r.nextInt(100000));
                }
                latch.countDown();
            });
        }
        Arrays.asList(ths).forEach(t->t.start());
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

}
