package priv.wzb.jvm.ref;

/**
 * @author Satsuki
 * @time 2020/7/4 11:23
 * @description:
 * 强引用的对象就是只要有句柄指向它对象就不会被回收
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        // 定义默认强引用
        Object obj1 = new Object();
        // 添加一个指向该对象的引用
        Object obj2 = obj1;
        // 置空
        obj1 = null;
        System.gc();
        System.out.println(obj2);
    }
}
