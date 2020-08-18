package priv.wzb.javabook.rf.streams;

import java.util.*;

/**
 * @author Satsuki
 * @time 2020/8/13 22:11
 * @description:
 */
public class Machine2 {
    public static void main(String[] args) {
        Arrays.stream(new Operations[]{
                ()-> Operations.show("Bing"),
                ()-> Operations.show("Crack"),
                ()-> Operations.show("Twist"),
                ()-> Operations.show("Pop"),
        }).forEach(Operations::execute);
    }
}
