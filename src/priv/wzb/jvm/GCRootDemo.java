package priv.wzb.jvm;

import sun.misc.GC;

/**
 * @author Satsuki
 * @time 2020/6/30 16:17
 * @description:
 * Java 可以做GCRoots的对象
 * 1.虚拟机栈(栈帧中的局部变量区,也叫做局部变量表
 * 2.方法区中的类静态属性引用的对象。
 * 3.方法区中常量引用的对象
 * 4.本地方法栈中N( Native方法)引用的对象
 */
public class GCRootDemo {
    private byte[] byteArray = new byte[100*1024*1024];

    // 2.方法区中的类静态属性引用的对象。
//    private static GCRootDemo2 t2;

    // 3.方法区中常量引用的对象
//    private static final GCRootDemo3 t3 = new GCRootDemo3();

    // 1.虚拟机栈(栈帧中的局部变量区,也叫做局部变量表
    // m1 是方法存放在jvm栈中就达成虚拟机栈的root条件
    // 因此内部的t1对象为由root可达的对象可存活不会被回收
    public static void m1(){
        GCRootDemo t1 = new GCRootDemo();
        System.gc();
        System.out.println("第一次GC完成");
    }

    public static void main(String[] args) {
        m1();
    }
}
