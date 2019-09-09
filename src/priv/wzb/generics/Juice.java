package priv.wzb.generics;

/**
 * @author Satsuki
 * @time 2019/6/11 15:26
 * @description:
 */
public class Juice extends Water {
    public Juice(String name) {
        super(name);
    }
    public void sayName(){
        System.out.println("果汁===：" + getName());
    }
}
