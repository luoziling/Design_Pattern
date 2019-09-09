package priv.wzb.design_pattern.structural_pattern.decoratorpattern.sxt;

/**
 * @author Satsuki
 * @time 2019/8/21 22:40
 * @description:
 * ConcreteDecorator具体装饰角色
 */
public class FlyCar extends SuperCar {

    public FlyCar(ICar car) {
        super(car);
    }

    public void fly(){
        System.out.println("fly on the sky");
    }

    @Override
    public void move() {
        super.move();
        fly();
    }
}
