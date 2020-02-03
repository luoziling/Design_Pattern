package priv.wzb.design_pattern.createdpattern.prototypepattern.classic;

/**
 * @author Satsuki
 * @time 2020/2/3 14:02
 * @description:
 * 具体原型类，它实现在抽象原型类中声明的克隆方法，
 * 在克隆方法中返回自己的一个克隆对象
 */
public class ConcretePrototype extends Prototype {

    private String attr;

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    /**
     * 克隆方法
     * @return 原型的克隆对象
     */
    @Override
    public Prototype clone() {
        Prototype prototype = new ConcretePrototype();
        ((ConcretePrototype) prototype).setAttr(this.attr);

        return prototype;
    }
}
