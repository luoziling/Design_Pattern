package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 15:51
 **/

public class Bing implements Operations {
    @Override
    public void execute() {
        Operations.show("Bing");
    }
}
