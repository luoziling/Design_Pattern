package priv.wzb.javabook.rf.functional;

/**
 * @author Satsuki
 * @time 2020/8/12 21:36
 * @description:
 */
public class TriFunctionTest {
    static int f(int i, long l, double d) { return 99; }
    public static void main(String[] args) {
        TriFunction<Integer, Long, Double, Integer> tf =
                TriFunctionTest::f;
        tf = (i, l, d) -> 12;
    }
}
@FunctionalInterface
interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}