package priv.wzb.jvm.ref;

import java.lang.ref.WeakReference;

/**
 * @author Satsuki
 * @time 2020/7/4 11:36
 * @description:
 * 弱引用不管内存是否足够触发GC就回收
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());

        // 回收前把o1置null否则还是强引用
        o1 = null;
        // 回收
        System.gc();

        System.out.println(o1);
        System.out.println(weakReference.get());

    }
}
