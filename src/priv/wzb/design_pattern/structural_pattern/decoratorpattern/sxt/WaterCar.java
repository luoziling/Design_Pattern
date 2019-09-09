package priv.wzb.design_pattern.structural_pattern.decoratorpattern.sxt;

/**
 * @author Satsuki
 * @time 2019/8/21 22:42
 * @description:
 */
public class WaterCar extends SuperCar{
    public WaterCar(ICar car) {
        super(car);
    }

    public void swim(){
        System.out.println("run above water");
    }

    @Override
    public void move() {
        super.move();
        swim();
    }
}
