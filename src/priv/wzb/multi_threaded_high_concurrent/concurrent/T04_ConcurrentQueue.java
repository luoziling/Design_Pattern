package priv.wzb.multi_threaded_high_concurrent.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Satsuki
 * @time 2019/6/1 13:50
 * @description:
 */
public class T04_ConcurrentQueue {
    public static void main(String[] args) {
        Queue<String> strs = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            strs.offer("a" + i);
        }
        System.out.println(strs);
        System.out.println(strs.size());
        System.out.println(strs.poll());
        System.out.println(strs.peek());
        System.out.println(strs.size());
    }
}
