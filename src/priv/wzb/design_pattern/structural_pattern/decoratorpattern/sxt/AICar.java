package priv.wzb.design_pattern.structural_pattern.decoratorpattern.sxt;

/**
 * @author Satsuki
 * @time 2019/8/21 22:43
 * @description:
 */
public class AICar extends SuperCar {
    public AICar(ICar car) {
        super(car);
    }

    public void autoMove(){
        System.out.println("auto move");
    }

    @Override
    public void move() {
        super.move();
        autoMove();
    }
}
