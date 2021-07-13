package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description: 使用匿名内部类实现多继承
 * @author: wangzibai
 * @create: 2020-08-31 17:45
 **/
class D{}

abstract class E{}

class Z extends D{
    E makeE(){
        return new E() {
        };
    }
}
public class MultiImplementation {
    static void takesD(D d) {}
    static void takesE(E e) {}

    public static void main(String[] args) {
        Z z = new Z();
        takesD(z);
        takesE(z.makeE());
    }
}
