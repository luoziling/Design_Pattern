package priv.wzb.design_pattern.dependency_inversion.weak;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-04 16:55
 **/

public class Car {
    private Framework framework;

    public Car(Framework framework) {
        this.framework = framework;
    }

    public void run(){

    }
}
