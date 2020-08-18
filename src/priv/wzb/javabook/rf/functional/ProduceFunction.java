package priv.wzb.javabook.rf.functional;
import java.util.function.*;
/**
 * @author Satsuki
 * @time 2020/8/12 21:40
 * @description:
 */
public class ProduceFunction {
    static FuncSS produce(){
        return s->s.toLowerCase();
    }

    public static void main(String[] args) {
        FuncSS f = produce();
        System.out.println(f.apply("YELLING"));
    }
}
interface
FuncSS extends Function<String, String> {} // [1]

