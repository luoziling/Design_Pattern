package priv.wzb.javabook.generics;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-14 19:04
 * @description:
 **/

public class GenericHolder<T> {
    private T a;

    public GenericHolder() {
    }

    public T getA() {
        return a;
    }

    public void setA(T a) {
        this.a = a;
    }

    public static void main(String[] args) {
        GenericHolder<Integer> h3 = new GenericHolder<>();
        h3.setA(1);
        Integer a = h3.getA();
        //- h3.set("Not an Automobile"); // 报错
    }
}
