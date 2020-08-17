package priv.wzb.javabook.streams;

import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-13 16:55
 **/

public class StreamOf {
    public static void main(String[] args) {
        Stream.of(new Bubble(1),new Bubble(2),new Bubble(3))
                .forEach(System.out::println);
        Stream.of("It's ", "a ", "wonderful ", "day ", "for ", "pie!")
                .forEach(System.out::print);
        System.out.println();
        Stream.of(3.14159, 2.718, 1.618)
                .forEach(System.out::println);
    }
}
