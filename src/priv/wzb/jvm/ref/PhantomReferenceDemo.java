package priv.wzb.jvm.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @author Satsuki
 * @time 2020/7/4 12:03
 * @description:
 * 虚引用
 */
public class PhantomReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o1,referenceQueue);
        // GC前，虚引用不管啥get都返回null
        System.out.println(o1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("==================");

        // 回收对象查看引用队列
        o1 = null;
        System.gc();
        Thread.sleep(500);


        // 回收后都是null但引用队列在回收前添加了一个被回收的对象
        // 虚引用GC后会放入引用队列
        // 虚引用一般配合引用队列使用
        System.out.println(o1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }
}
