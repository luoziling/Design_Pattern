package priv.wzb.design_pattern.structural_pattern.decoratorpattern.sxt;


/**
 * @author Satsuki
 * @time 2019/8/21 22:44
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Car car = new Car();
        car.move();

        System.out.println("增加新功能，飞行---------------");
        FlyCar flyCar = new FlyCar(car);
        flyCar.move();
        System.out.println("增加新功能，swim---------------");
//        WaterCar waterCar = new WaterCar(car);
        WaterCar waterCar = new WaterCar(flyCar);
        waterCar.move();
        System.out.println("增加新功能，auto---------------");
//        AICar aiCar = new AICar(car);
        AICar aiCar = new AICar(waterCar);
        aiCar.move();

    }
}
