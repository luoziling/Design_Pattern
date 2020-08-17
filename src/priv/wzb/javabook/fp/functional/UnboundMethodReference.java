package priv.wzb.javabook.fp.functional;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-12 18:05
 **/

public class UnboundMethodReference {
    public static void main(String[] args) {
//        MakeString ms = X::f;
        // 使用未绑定的引用，
        // 函数式方法的签名（接口中的单个方法）不再与方法引用的签名完全匹配。
        // 需要一个对象来调用方法
        TransformX sp = X::f;
        X x = new X();
        System.out.println(sp.transform(x));
        System.out.println(x.f());
    }
}
