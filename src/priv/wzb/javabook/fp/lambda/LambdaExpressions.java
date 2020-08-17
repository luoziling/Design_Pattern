package priv.wzb.javabook.fp.lambda;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-12 16:59
 **/

public class LambdaExpressions {
    static Body bod = h -> h+"No Parens!";

    static Body bod2 = (h) -> h + "More details";

    static Description desc = ()-> "Short info";

    static Multi mult = (h,n) -> h+n;

    static Description moreLines = ()->{
        System.out.println("moreLines()");
        return "from moreLines";
    };

    public static void main(String[] args) {
        System.out.println(bod.detailed("Oh!"));
        System.out.println(bod2.detailed("Hi!"));
        System.out.println(desc.brief());
        System.out.println(mult.twoArg("Pi! ", 3.14159));
        System.out.println(moreLines.brief());
    }
}
