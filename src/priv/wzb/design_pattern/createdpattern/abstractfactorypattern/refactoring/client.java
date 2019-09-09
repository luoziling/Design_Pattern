package priv.wzb.design_pattern.createdpattern.abstractfactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 19:10
 * @description:
 */
public class client {
    public static void main(String[] args) {
        //使用抽象层定义
        SkinFactory factory;
        Button bt;
        TextField tf;
        ComboBox cb;
        factory = new SpringSkinFactory();
        bt = factory.createButton();
        tf = factory.createTextField();
        cb = factory.createComboBox();
        bt.display();
        tf.display();
        cb.display();
    }
}
