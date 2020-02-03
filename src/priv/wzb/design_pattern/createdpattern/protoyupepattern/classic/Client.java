package priv.wzb.design_pattern.createdpattern.protoyupepattern.classic;

/**
 * @author Satsuki
 * @time 2020/2/3 14:05
 * @description:
 * 客户类，使用具体原型类创建一个自己的复制对象
 */
public class Client {
    public static void main(String[] args) {
        ConcretePrototype prototype = new ConcretePrototype();
        prototype.setAttr("Sunny");
        ConcretePrototype copy = (ConcretePrototype) prototype.clone();
    }
}
