package priv.wzb.javabook.files;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-03 17:47
 **/

public class ListOfLines {
    public static void main(String[] args) throws Exception {
        Files.readAllLines(
                Paths.get("D:\\Learning\\Design_Pattern\\txt\\a.txt")
        ).stream()
                .filter(line-> !line.startsWith("//"))
                .map(line->line.substring(0,line.length()/2))
                .forEach(System.out::println);
    }
}
