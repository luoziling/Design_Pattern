package priv.wzb.javabook.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-13 17:17
 **/

// Supplier代表函数式编程中无参数； 返回类型任意的函数
public class RandomWords implements Supplier<String> {
    List<String> words = new ArrayList<>();
    Random rand = new Random(47);
    RandomWords(String fname) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(fname));
        // 略过第一行
        for (String line : lines.subList(1,lines.size())) {
            for (String word : line.split("[ .?,]+")) {
                words.add(word.toLowerCase());

            }
        }
    }

    @Override
    public String get() {
        return words.get(rand.nextInt(words.size()));
    }

    @Override
    public String toString() {
        // collect 根据参数来结合所有流元素。
        return words.stream()
                .collect(Collectors.joining(" "));

    }


    public static void main(String[] args) throws Exception {
        System.out.println(
                Stream.generate(new RandomWords("src/priv/wzb/javabook/streams/Cheese.dat"))
                .limit(10)
                .collect(Collectors.joining(" "))
        );
    }
}
