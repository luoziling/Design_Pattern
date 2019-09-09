package priv.wzb.design_pattern.structural_pattern.bridgepattern.classic;

/**
 * @author Satsuki
 * @time 2019/8/13 17:43
 * @description:对于另一“抽象部分”维度而言，其典型的抽象类代码如下所示：
 */
public abstract class Abstraction {
    //定义实现类的抽象接口给
    protected Implementor impl;

    public Abstraction(Implementor impl) {
        this.impl = impl;
    }

    public abstract void operation();
}
