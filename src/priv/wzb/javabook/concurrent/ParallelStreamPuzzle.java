package priv.wzb.javabook.concurrent;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-21 17:16
 * @description:
 **/

public class ParallelStreamPuzzle {
    static class IntGenerator implements Supplier<Integer>{
        private int current = 0;

        @Override
        public Integer get() {
            return current++;
        }
    }

    public static void main(String[] args) {
        List<Integer> x = Stream.generate(new IntGenerator())
                .limit(10)
//                .parallel()
                .collect(Collectors.toList());
        System.out.println(x);
    }
}
