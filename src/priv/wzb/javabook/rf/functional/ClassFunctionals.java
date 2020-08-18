package priv.wzb.javabook.rf.functional;
import java.util.*;
import java.util.function.*;
/**
 * @author Satsuki
 * @time 2020/8/12 21:29
 * @description:
 */
public class ClassFunctionals {
    static AA f1() { return new AA(); }
    static int f2(AA aa1, AA aa2) { return 1; }
    static void f3(AA aa) {}
    static void f4(AA aa, BB bb) {}
    static CC f5(AA aa) { return new CC(); }
    static CC f6(AA aa, BB bb) { return new CC(); }
    static boolean f7(AA aa) { return true; }
    static boolean f8(AA aa, BB bb) { return true; }
    static AA f9(AA aa) { return new AA(); }
    static AA f10(AA aa1, AA aa2) { return new AA(); }
    public static void main(String[] args) {
        // 无参数；
        //返回类型任意
        Supplier<AA> s = ClassFunctionals::f1;
        s.get();
        // 2 参数类型相同;
        //返回整型
        Comparator<AA> c = ClassFunctionals::f2;
        c.compare(new AA(), new AA());
        //1 参数；
        //无返回值
        Consumer<AA> cons = ClassFunctionals::f3;
        cons.accept(new AA());
        // 2 参数类型不同
        BiConsumer<AA,BB> bicons = ClassFunctionals::f4;
        bicons.accept(new AA(), new BB());
        // 1 参数；
        //返回类型不同
        Function<AA,CC> f = ClassFunctionals::f5;
        CC cc = f.apply(new AA());
        // 2 参数类型不同
        BiFunction<AA,BB,CC> bif = ClassFunctionals::f6;
        cc = bif.apply(new AA(), new BB());
        // 2 参数；
        //返回布尔型
        Predicate<AA> p = ClassFunctionals::f7;
        boolean result = p.test(new AA());
        // 2 参数类型不同
        BiPredicate<AA,BB> bip = ClassFunctionals::f8;
        result = bip.test(new AA(), new BB());
        // 1 参数；
        //返回类型相同
        UnaryOperator<AA> uo = ClassFunctionals::f9;
        AA aa = uo.apply(new AA());
        // 2 参数类型相同；
        //返回类型相同
        BinaryOperator<AA> bo = ClassFunctionals::f10;
        aa = bo.apply(new AA(), new AA());
    }
}
