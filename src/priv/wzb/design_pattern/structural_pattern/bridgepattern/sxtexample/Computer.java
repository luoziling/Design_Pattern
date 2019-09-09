package priv.wzb.design_pattern.structural_pattern.bridgepattern.sxtexample;

/**
 * @author Satsuki
 * @time 2019/8/13 17:10
 * @description:
 */
public abstract class Computer {

    public Brand brand;

    public Computer(Brand brand) {
        this.brand = brand;
    }

    public void sale(){
        brand.sale();
    }
}
