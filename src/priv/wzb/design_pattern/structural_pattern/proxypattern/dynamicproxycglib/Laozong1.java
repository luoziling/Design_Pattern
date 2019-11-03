package priv.wzb.design_pattern.structural_pattern.proxypattern.dynamicproxycglib;

/**
 * @author Satsuki
 * @time 2019/8/17 17:37
 * @description:
 */
public class Laozong1 extends Laozong {
    @Override
    public void chifan() {
        System.out.println("预约时间");
        super.chifan();
        System.out.println("备注");
    }
}
