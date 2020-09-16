package priv.wzb.javabook.generics;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-14 19:11
 * @description:
 **/

public class Tuple2<A, B> {
    public final A a1;
    public final B a2;

    public Tuple2(A a1, B a2) {
        this.a1 = a1;
        this.a2 = a2;
    }
    public String rep(){
        return a1+","+a2;
    }
    @Override
    public String toString() {
        return "(" + rep() + ")";
    }
}
