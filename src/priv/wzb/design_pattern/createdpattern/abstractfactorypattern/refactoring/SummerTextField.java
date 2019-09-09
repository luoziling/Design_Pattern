package priv.wzb.design_pattern.createdpattern.abstractfactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 19:05
 * @description:Summer文本框类：具体产品
 */
public class SummerTextField implements TextField {
    @Override
    public void display() {
        System.out.println("显示蓝色边框文本框。");
    }
}
