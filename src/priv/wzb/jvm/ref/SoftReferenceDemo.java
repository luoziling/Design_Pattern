package priv.wzb.jvm.ref;

import com.sun.corba.se.spi.ior.ObjectKey;

import java.lang.ref.SoftReference;

/**
 * @author Satsuki
 * @time 2020/7/4 11:27
 * @description:
 * 软引用
 * 内存足够不被回收
 * 内存不够会被回收
 * 用于缓存
 */
public class SoftReferenceDemo {


    public static void softRefMemoryEnouth(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        // 回收下，内存够用不被回收
        o1 = null;
        System.gc();

        System.out.println(o1);
        System.out.println(softReference.get());

    }

    /**
     * JVM配置创建大对象配置小内存导致OOM，内存不够用，看软引用是否被回收
     * -Xms5m -Xmx5m -XX:+PrintGCDetails
     *
     */
    public static void softRefMemoryNotEnouth(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();

        try {
            byte[] bytes = new byte[30*1024*1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(softReference.get());
        }

    }

    public static void main(String[] args) {

        softRefMemoryEnouth();

//        softRefMemoryNotEnouth();

    }

}
