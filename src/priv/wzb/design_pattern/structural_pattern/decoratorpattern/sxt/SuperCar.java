package priv.wzb.design_pattern.structural_pattern.decoratorpattern.sxt;

/**
 * @author Satsuki
 * @time 2019/8/21 22:39
 * @description:
 * Decorator 装饰角色
 */
public class SuperCar implements ICar {
    private ICar car;

    public SuperCar(ICar car) {
        this.car = car;
    }

    @Override
    public void move() {
        car.move();
    }
}
