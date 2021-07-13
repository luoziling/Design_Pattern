package priv.wzb.javabook.generics;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-15 18:45
 * @description:
 **/

public class Manipulator2<T extends HasF> {
    private T obj;

    public Manipulator2(T obj) {
        this.obj = obj;
    }

    public void manipulate(){
        obj.f();
    }
}
