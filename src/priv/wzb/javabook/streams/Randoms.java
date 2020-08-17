package priv.wzb.javabook.streams;

import java.util.ArrayList;
import java.util.Random;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-13 16:36
 **/

public class Randoms {
    public static void main(String[] args) {
        new Random(47)
                .ints(5,20)
                .distinct()
                .limit(7)
                .sorted()
                .forEach(System.out::println);
    }
}
