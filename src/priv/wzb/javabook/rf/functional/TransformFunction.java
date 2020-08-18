package priv.wzb.javabook.rf.functional;
import java.util.function.*;
/**
 * @author Satsuki
 * @time 2020/8/12 21:42
 * @description:
 */
class I {
    @Override
    public String toString() { return "I"; }
}

class O {
    @Override
    public String toString() { return "O"; }
}

public class TransformFunction {
    static Function<I,O> transform(Function<I,O> in) {
        return in.andThen(o -> {
            System.out.println(o);
            return o;
        });
    }
    public static void main(String[] args) {
        // 函数联动
        // 生成两个函数
        // 一个输出I一个输出O，O的是上面transform方法中的调用andThen方法所以在后面
        Function<I,O> f2 = transform(i -> {
            System.out.println(i);
            return new O();
        });
        O o = f2.apply(new I());
    }
}
