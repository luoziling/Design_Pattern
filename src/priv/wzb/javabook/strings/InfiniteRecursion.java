package priv.wzb.javabook.strings;

import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-07 15:44
 * @description:
 **/

public class InfiniteRecursion {
    @Override
    public String toString() {
        // Exception in thread "main" java.lang.StackOverflowError
        // this会自动转为toString从而导致无限嵌套
        // 方法调用方法产生栈溢出
//        return "InfiniteRecursion address: " + this + "\n";
        // 正确的打印方式
        return "InfiniteRecursion address: " + super.toString() + "\n";
    }

    public static void main(String[] args) {
        Stream.generate(InfiniteRecursion::new)
                .limit(10)
                .forEach(System.out::println);
    }
}
