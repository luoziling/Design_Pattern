package priv.wzb.design_pattern.structural_pattern.bridgepattern.sxtexample;

/**
 * @author Satsuki
 * @time 2019/8/13 17:09
 * @description:
 */
public class Lenovo implements Brand {
    @Override
    public void sale() {
        System.out.println("推销Lenovo电脑");
    }
}
