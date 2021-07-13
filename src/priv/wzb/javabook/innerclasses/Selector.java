package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-31 15:44
 **/

public interface Selector {
    boolean end();
    Object current();
    void next();
}

