package priv.wzb.multi_threaded_high_concurrent.block;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2020/5/25 16:19
 * @description:
 *
 * 队列
 * 阻塞队列
 * 阻塞队列有没有好的一面
 * 不得不阻塞如何管理
 */
public class BolckingQueueDemo {
    public static void main(String[] args) throws Exception{
//        List list = null;

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // add 和remove如果满或空直接抛出异常，element判断队列是否为空并拿出队首元素
//        System.out.println(blockingQueue.add("a"));
//        System.out.println(blockingQueue.add("b"));
//        System.out.println(blockingQueue.add("c"));
//
//
////        System.out.println(blockingQueue.add("x"));
//        System.out.println(blockingQueue.element());
//
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());


        // offer、peek、poll
        // offer如果队满返回false不会抛出异常
        // peek查看队首元素
        // poll拿出队首元素，拿不到返回null
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("x"));
//
//        System.out.println(blockingQueue.peek());
//
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());


        // put,如果队满，并不会直接丢掉，而是放到一个等待区，当队列有未知进入队列
        new Thread(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                // 拿出一个元素
                System.out.println(blockingQueue.take());
                System.out.println("blockingQueue = " + blockingQueue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"获取线程").start();

        blockingQueue.put("a");
        System.out.println("blockingQueue = " + blockingQueue);
        blockingQueue.put("a");
        System.out.println("blockingQueue = " + blockingQueue);
        blockingQueue.put("a");
        System.out.println("blockingQueue = " + blockingQueue);
        System.out.println("=====================");
        blockingQueue.put("x");// put 队列满会一直阻塞直到put数据或者中断退出
        System.out.println("blockingQueue = " + blockingQueue);


        // 设置插入时长，超时退出
//        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
//        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
//        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
//        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));

//        TimeUnit.MILLISECONDS.sleep(1000);


        System.out.println(blockingQueue.take());
        System.out.println("blockingQueue = " + blockingQueue);
        System.out.println(blockingQueue.take());
        System.out.println("blockingQueue = " + blockingQueue);
        System.out.println(blockingQueue.take());
        System.out.println("blockingQueue = " + blockingQueue);

//        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));

    }

}
