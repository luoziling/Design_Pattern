package priv.wzb.javabook.reuse;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-05 14:32
 **/
class A{

}
class B{

}
public class MulExtends extends A{
    /**
     * 内部类实现多extends
     * 非静态的内部类在初始化时
     * 会默认包含一个指向外部类的指针以调用外部类中的属性与方法
     */
    class C extends B{

    }
}
