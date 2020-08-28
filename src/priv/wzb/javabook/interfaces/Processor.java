package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 16:17
 **/
public interface Processor {
    default String name() {
        return getClass().getSimpleName();
    }

    Object process(Object input);
}
