package priv.wzb.design_pattern.createdpattern.abstractfactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 19:07
 * @description:界面皮肤工厂接口：抽象工厂
 */
public interface SkinFactory {
    public Button createButton();
    public TextField createTextField();
    public ComboBox createComboBox();
}
