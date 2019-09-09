package priv.wzb.generics;

/**
 * @author Satsuki
 * @time 2019/6/11 15:28
 * @description:
 */
public class AppleJuice extends Juice {
    public AppleJuice(String name) {
        super(name);
    }

    @Override
    public void sayName() {
        System.out.println("苹果汁===：" + getName());
    }
}
