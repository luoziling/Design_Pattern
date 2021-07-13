package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description: 匿名内部类的类似构造器使用
 * @author: wangzibai
 * @create: 2020-08-31 17:15
 **/
abstract class Base {
    Base(int i) {
        System.out.println("Base constructor, i = " + i);
    }
    public abstract void f();
}
public class AnonymousConstructor {
    public static Base getBase(int i){
        return new Base(i) {
            { System.out.println(
                    "Inside instance initializer"); }
            @Override
            public void f() {
                System.out.println("In anonymous f()");
            }
        };
    }

    public static void main(String[] args) {
        Base base = getBase(47);
        base.f();
    }
}
