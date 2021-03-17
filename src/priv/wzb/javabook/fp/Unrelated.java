package priv.wzb.javabook.fp;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-12 16:33
 **/

public class Unrelated {
    /**
     * 只是定义一个静态方法 与Strategy的接口参数/返回值 保持一致
     * @param msg
     * @return
     */
    static String twice(String msg){
        return msg + " " + msg;
    }
}
