package priv.wzb.javabook.strings;

import org.junit.Test;

import java.util.Arrays;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-07 16:24
 * @description:
 **/

public class Splitting {
    public static String knights =
            "Then, when you have found the shrubbery, " +
                    "you must cut down the mightiest tree in the " +
                    "forest...with... a herring!";
    public static void split(String regex){
        System.out.println(Arrays.toString(knights.split(regex)));
    }

    public static void main(String[] args) {
        split(" "); // Doesn't have to contain regex chars
        split("\\W+"); // Non-word characters
        split("n\\W+"); // 'n' followed by non-words
    }

    @Test
    public void tSplit(){
        System.out.println(
                knights.replaceFirst("f\\w+", "located"));
        System.out.println(
                knights.replaceAll("shrubbery|tree|herring","banana"));
    }
}
