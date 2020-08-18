package priv.wzb.javabook.rf.functional;
import java.util.function.*;
/**
 * @author Satsuki
 * @time 2020/8/12 21:49
 * @description:
 */
public class SharedStorage {
    public static void main(String[] args) {
        Closure1 c1 = new Closure1();
        IntSupplier f1 = c1.makeFun(0);
        IntSupplier f2 = c1.makeFun(0);
        IntSupplier f3 = c1.makeFun(0);
        System.out.println(f1.getAsInt());
        System.out.println(f2.getAsInt());
        System.out.println(f3.getAsInt());
    }
}
