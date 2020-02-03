package priv.wzb.design_pattern.createdpattern.protoyupepattern.classic;

/**
 * @author Satsuki
 * @time 2020/2/3 13:59
 * @description:
 * 抽象原型类：它是声明克隆方法的接口，是所有具体原型类的公共父类，
 * 它可以是抽象类也可以是接口，甚至还可以是具体实现类。
 */
public abstract class Prototype {
    public abstract Prototype clone();
}
