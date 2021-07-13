package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 15:52
 **/

public class Crack implements Operations {
    @Override
    public void execute() {
        Operations.show("Crack");
    }
}
