package priv.wzb.design_pattern.dependency_inversion.weak;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-04 16:58
 **/

public class Test {
    public static void main(String[] args) {
        Tire tire = new Tire();
        Bottom bottom = new Bottom(tire);
        Framework framework = new Framework(bottom);
        Car myCar = new Car(framework);
        myCar.run();
    }
}
