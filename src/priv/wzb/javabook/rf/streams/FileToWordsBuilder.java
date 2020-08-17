package priv.wzb.javabook.rf.streams;

import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
/**
 * @author Satsuki
 * @time 2020/8/13 22:00
 * @description:
 */
public class FileToWordsBuilder {
    Stream.Builder<String> builder = Stream.builder();

    public FileToWordsBuilder(String filePath) throws Exception {
        Files.lines(Paths.get(filePath))
                .skip(1) // 略过开头
                .forEach(line->{
                    for (String w : line.split("[ .?,]+"))
                        builder.add(w);
                });
    }
    Stream<String> stream() {
        return builder.build();
    }

    public static void main(String[] args) throws Exception {
        new FileToWordsBuilder("Cheese.dat")
                .stream()
                .limit(7)
                .map(w -> w + " ")
                .forEach(System.out::print);
    }
}
