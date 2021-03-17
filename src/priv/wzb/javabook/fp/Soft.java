package priv.wzb.javabook.fp;

/**
 * @program: Design_Pattern
 * @description: soft
 * @author: wangzibai
 * @create: 2020-08-12 16:30
 **/

public class Soft implements Strategy {
    /**
     * 接口的实现 参数/返回值 一致
     * @param msg
     * @return
     */
    @Override
    public String approach(String msg) {
        return msg.toLowerCase() + "?";
    }
}
