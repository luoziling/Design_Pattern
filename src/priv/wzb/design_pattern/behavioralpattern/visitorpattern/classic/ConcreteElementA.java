package priv.wzb.design_pattern.behavioralpattern.visitorpattern.classic;

/**
 * @author Satsuki
 * @time 2019/6/9 19:37
 * @description:
 */
public class ConcreteElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
//        visitor.visit(this);
    }
    public void operationA()
    {
        //业务方法
    }
}
