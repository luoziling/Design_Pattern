package priv.wzb.javabook.strings;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-07 16:41
 * @description:
 **/

public class Rudolph {
    public static void main(String[] args) {
        for(String pattern : new String[]{
                "Rudolph",
                "[rR]udolph",
                "[rR][aeiou][a-z]ol.*",
                "R.*" })
            System.out.println("Rudolph".matches(pattern));
    }
}
