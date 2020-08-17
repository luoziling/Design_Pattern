package priv.wzb.javabook.fp;

/**
 * @program: Design_Pattern
 * @description: soft
 * @author: wangzibai
 * @create: 2020-08-12 16:30
 **/

public class Soft implements Strategy {
    @Override
    public String approach(String msg) {
        return msg.toLowerCase() + "?";
    }
}
