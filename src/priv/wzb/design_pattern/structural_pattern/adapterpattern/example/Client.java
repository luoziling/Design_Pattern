package priv.wzb.design_pattern.structural_pattern.adapterpattern.example;

/**
 * @author Satsuki
 * @time 2019/6/18 17:51
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        CarController car;
        car = new PoliceCarAdapter();
        car.move();
        car.phonate();
        car.twinkle();
    }
}
