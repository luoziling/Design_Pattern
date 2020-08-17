package priv.wzb.jvm.ref;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author Satsuki
 * @time 2020/7/4 11:45
 * @description:
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        myHashmap();

        System.out.println("=================");

        myWeakHashmap();

        /**
         * {1=hashMap}
         * {1=hashMap}
         * {1=hashMap}
         * =================
         * {1=hashMap}
         * {1=hashMap}
         * {}
         */
    }



    private static void myHashmap() {
        HashMap<Integer,String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "hashMap";
        map.put(key,value);
        System.out.println(map);

        // 这里虽然key被置为null
        // 但是put的时候会把对象传给hashmap内部的node对象所以又有别的对象引用该key
        // 不会被置为null，仍然可以看到
        key = null;
        System.out.println(map);

        // 即使GC key对象也是还存在
        System.gc();
        System.out.println(map);
    }

    private static void myWeakHashmap() {
        WeakHashMap<Integer,String> map = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "WeakHashMap";
        map.put(key,value);
        System.out.println(map);

        // 这里ey被置为null
        // 看看WeakHashMap如何工作
        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }
}
