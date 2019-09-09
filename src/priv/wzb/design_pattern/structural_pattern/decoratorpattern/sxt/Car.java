package priv.wzb.design_pattern.structural_pattern.decoratorpattern.sxt;

/**
 * @author Satsuki
 * @time 2019/8/21 22:38
 * @description:
 * ConcreteComponent 具体构建角色（真实对象）
 */
public class Car implements ICar {
    @Override
    public void move() {
        System.out.println("move on the ground");
    }
}
