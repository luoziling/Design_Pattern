package priv.wzb.design_pattern.structural_pattern.bridgepattern.sxtexample;

/**
 * @author Satsuki
 * @time 2019/8/13 17:11
 * @description:
 */
public class Desktop extends Computer {
    public Desktop(Brand brand) {
        super(brand);
    }

    @Override
    public void sale() {
        super.sale();
        System.out.println("销售台式机");
    }
}
