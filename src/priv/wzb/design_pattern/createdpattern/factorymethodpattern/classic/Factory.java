package priv.wzb.design_pattern.createdpattern.factorymethodpattern.classic;


/**
 * @author Satsuki
 * @time 2019/6/9 18:32
 * @description:在工厂方法模式中，我们不再提供一个统一的工厂类来创建所有的产品对象， 而是针对不同的产品提供不同的工厂，系统提供一个与产品等级结构对应的工厂等级结构。
 * 工厂方法模式定义如下：
 * 工厂方法模式(Factory Method Pattern)：定义一个用于创建对象的接口，让子类决定将哪一个类实例化。
 * 工厂方法模式让一个类的实例化延迟到其子类。
 * 厂方法模式又简称为工厂模式(Factory Pattern)，
 * 又可称作虚拟构造器模式(Virtual Constructor Pattern)或多态工厂模式(Polymorphic Factory Pattern)。
 * 工厂方法模式是一种类创建型模式。
 */
public interface Factory {
    public Product factoryMethod();

    public Product1 factoryMethod1();
}
