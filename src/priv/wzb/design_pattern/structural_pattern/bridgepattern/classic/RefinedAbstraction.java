package priv.wzb.design_pattern.structural_pattern.bridgepattern.classic;

/**
 * @author Satsuki
 * @time 2019/8/13 17:45
 * @description:
 * 在抽象类Abstraction中定义了一个实现类接口类型的成员对象impl，
 * 再通过注入的方式给该对象赋值，一般将该对象的可见性定义为protected，
 * 以便在其子类中访问Implementor的方法，
 * 其子类一般称为扩充抽象类或细化抽象类(RefinedAbstraction)，
 * 典型的RefinedAbstraction类代码如下所示：
 */
public class RefinedAbstraction extends Abstraction {

    public RefinedAbstraction(Implementor impl) {
        super(impl);
    }

    @Override
    public void operation() {
        //业务代码
        //调用实现类的方法
        impl.operationImpl();
        //业务代码
    }
}
