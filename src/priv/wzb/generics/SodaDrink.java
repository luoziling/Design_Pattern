package priv.wzb.generics;

/**
 * @author Satsuki
 * @time 2019/6/11 15:22
 * @description:
 */
public class SodaDrink extends Water {
    public SodaDrink(String name) {
        super(name);
    }

    @Override
    public void sayName() {
        System.out.println("汽水/苏打水===" + getName());
    }
}
