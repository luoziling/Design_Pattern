package priv.wzb.javabook.generics;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-14 19:14
 * @description:
 **/

public class Tuple3<A,B,C> extends Tuple2<A,B> {
    public final C a3;

    public Tuple3(A a1, B a2, C a3) {
        super(a1, a2);
        this.a3 = a3;
    }
    @Override
    public String rep() {
        return super.rep() + ", " + a3;
    }
}
