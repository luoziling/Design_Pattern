package priv.wzb.javabook.strings;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-07 16:20
 * @description:
 **/

public class IntegerMatch {
    public static void main(String[] args) {
        System.out.println("-1234".matches("-?\\d+"));
        System.out.println("5678".matches("-?\\d+"));
        System.out.println("+911".matches("-?\\d+"));
        System.out.println("+911".matches("(-|\\+)?\\d+"));
    }
}
