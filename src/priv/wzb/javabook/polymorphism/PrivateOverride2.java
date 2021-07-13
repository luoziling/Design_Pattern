package priv.wzb.javabook.polymorphism;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-25 16:13
 **/

public class PrivateOverride2 {
    private void f(){
        System.out.println("PrivateOverride2.f");
    }

    public static void main(String[] args) {
        PrivateOverride2 po = new Derived2();
        po.f();
    }
}
