package priv.wzb.javabook.streams;

import java.util.Random;

/**
 * @program: Design_Pattern
 * @description:
 * @author: yuzuki
 * @create: 2020-08-6
 **/

public class Randoms {
    public static void main(String[] args) {
        new Random(47)
                // 创建随机数的流，
                .ints(5,20)
                // 唯一性
                .distinct()
                // 限制7个
                .limit(7)
                // 排序
                .sorted()
                // 遍历操作
                .forEach(System.out::println);
    }
}
