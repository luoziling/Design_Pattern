package priv.wzb.javabook.generics;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-15 18:42
 * @description:
 **/
class Manipulator<T>{
    private T obj;

    public Manipulator(T obj) {
        this.obj = obj;
    }

    public void manipulate(){
        //Cannot resolve method 'f' in 'T'
        // 产生擦除无法调用泛型的方法
//        obj.f();
    }
}
public class Manipulation {
    public static void main(String[] args) {
        HasF hf = new HasF();
        Manipulator<HasF> manipulator = new Manipulator<>(hf);
        manipulator.manipulate();
    }
}
