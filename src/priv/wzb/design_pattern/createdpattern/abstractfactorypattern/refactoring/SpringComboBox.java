package priv.wzb.design_pattern.createdpattern.abstractfactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 19:06
 * @description:Spring组合框类：具体产品
 */
public class SpringComboBox implements ComboBox {
    @Override
    public void display() {
        System.out.println("显示绿色边框组合框。");
    }
}
