package priv.wzb.jvm.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author Satsuki
 * @time 2020/7/4 11:56
 * @description:
 * 虚引用配合ReferenceQueue使用
 */
public class ReferenceQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

        WeakReference<Object> weakReference = new WeakReference<>(o1,referenceQueue);
        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());


        System.out.println("===================");

        // 回收对象查看引用队列
        o1 = null;
        System.gc();
        Thread.sleep(500);


        // 回收后都是null但引用队列在回收前添加了一个被回收的对象
        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());
    }
}
