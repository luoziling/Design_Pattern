package priv.wzb.javabook.enums;
// 静态引入枚举简化枚举调用

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-21 15:07
 **/
import static priv.wzb.javabook.enums.SpicinessEnum.*;
public class Burrito2 {
    SpicinessEnum degree;

    public Burrito2(SpicinessEnum degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "Burrito is "+degree;
    }

    public static void main(String[] args) {
        System.out.println(new Burrito2(NOT));
        System.out.println(new Burrito2(MEDIUM));
        System.out.println(new Burrito2(HOT));
    }
}
