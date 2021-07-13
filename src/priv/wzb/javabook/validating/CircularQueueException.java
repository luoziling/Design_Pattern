package priv.wzb.javabook.validating;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-02 18:11
 **/

public class CircularQueueException extends RuntimeException {
    public CircularQueueException(String why){
        super(why);
    }
}
