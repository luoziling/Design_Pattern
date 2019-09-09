package priv.wzb.design_pattern.structural_pattern.bridgepattern.sxtexample;

/**
 * @author Satsuki
 * @time 2019/8/13 17:13
 * @description:
 */
public class Laptop extends Computer {
    public Laptop(Brand brand) {
        super(brand);
    }

    @Override
    public void sale() {
        super.sale();
        System.out.println("sale Laptop");
    }
}
