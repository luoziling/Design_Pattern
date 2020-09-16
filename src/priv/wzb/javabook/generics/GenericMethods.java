package priv.wzb.javabook.generics;

import priv.wzb.multi_threaded_high_concurrent.thread_synchronization.T;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-15 17:39
 * @description:
 **/

public class GenericMethods {
    private int a;
    private static T b;
    public static  <T> void f(T x){
        System.out.println(x.getClass().getName());
//        System.out.println("a = " + a);
//        System.out.println("b = " + b);
    }
    public static void main(String[] args) {
        GenericMethods gm = new GenericMethods();
        gm.f("");
        gm.f(1);
        gm.f(1.0);
        gm.f(1.0F);
        gm.f('c');
        gm.f(gm);
    }
}
