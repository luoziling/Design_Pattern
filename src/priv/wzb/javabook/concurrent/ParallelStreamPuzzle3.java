package priv.wzb.javabook.concurrent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-21 17:30
 * @description:
 **/

public class ParallelStreamPuzzle3 {
    public static void main(String[] args) {
        List<Integer> x = IntStream.range(0,30)
                .peek(e-> System.out.println(e+": "+Thread.currentThread().getName()))
                .limit(10)
                .parallel()
                .boxed()
                .collect(Collectors.toList());
        System.out.println(x);
    }
}
