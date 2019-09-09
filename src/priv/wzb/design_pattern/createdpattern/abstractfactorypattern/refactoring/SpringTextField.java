package priv.wzb.design_pattern.createdpattern.abstractfactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 19:04
 * @description:Spring文本框类：具体产品
 */
public class SpringTextField implements TextField {
    @Override
    public void display() {
        System.out.println("显示绿色边框文本框。");
    }
}
