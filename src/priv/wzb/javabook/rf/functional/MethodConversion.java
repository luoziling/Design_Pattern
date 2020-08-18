package priv.wzb.javabook.rf.functional;

import java.util.function.BiConsumer;

/**
 * @author Satsuki
 * @time 2020/8/12 21:23
 * @description:
 */
public class MethodConversion {
    static void accept(In1 i1, In2 i2) {
        System.out.println("accept()");
    }
    static void someOtherName(In1 i1, In2 i2) {
        System.out.println("someOtherName()");
    }

    public static void main(String[] args) {
        BiConsumer<In1,In2> bic;

        bic = MethodConversion::accept;
        bic.accept(new In1(), new In2());
        bic = MethodConversion::someOtherName;
        bic.accept(new In1(), new In2());
    }
}
