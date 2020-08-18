package priv.wzb.javabook.rf.functional;

import java.util.function.IntSupplier;

/**
 * @author Satsuki
 * @time 2020/8/12 21:48
 * @description:
 */
public class Closure1 {
    int i;
    IntSupplier makeFun(int x) {
        return () -> x + i++;
    }
}
