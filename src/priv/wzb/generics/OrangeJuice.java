package priv.wzb.generics;

/**
 * @author Satsuki
 * @time 2019/6/11 15:27
 * @description:
 */
public class OrangeJuice extends Juice {
    public OrangeJuice(String name) {
        super(name);
    }

    @Override
    public void sayName() {
        System.out.println("橙汁===：" + getName());
    }
}
