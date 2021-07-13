package priv.wzb.javabook.polymorphism;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-25 16:14
 **/

public class Derived2 extends PrivateOverride2 {
//    @Override
    public void f() {
        System.out.println("public f()");
    }
    public static void main(String[] args) {
        PrivateOverride2 po = new Derived2();
//        po.f();
    }
}
