package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 15:53
 **/

public class Machine {
    public static void main(String[] args) {
        Operations.runOps(new Bing(),new Crack(),new Twist());
    }
}
