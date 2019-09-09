package priv.wzb.multi_threaded_high_concurrent.concurrent;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Satsuki
 * @time 2019/6/1 14:00
 * @description:
 */
public class T05_LinkedBlockingQueue {
    static BlockingQueue<String> strs = new LinkedBlockingDeque<>();
    static Random r = new Random();

    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                try {
                    strs.put("a" + i);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        },"p1").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                for(;;){
                    try {
                        System.out.println(Thread.currentThread().getName() + " take-" + strs.take());
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            },"c" + i).start();
        }
    }
}
