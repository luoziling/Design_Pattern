package priv.wzb.design_pattern.structural_pattern.proxypattern.staticproxy;

/**
 * @author Satsuki
 * @time 2019/8/12 15:57
 * @description:
 */
public class RealStar implements Star {
    @Override
    public void confer() {
        System.out.println("confer");
    }

    @Override
    public void signContract() {
        System.out.println("signContract");
    }

    @Override
    public void bookTicket() {
        System.out.println("bookTicket");
    }

    @Override
    public void sing() {
        System.out.println("sing");
    }

    @Override
    public void collectMoney() {
        System.out.println("collectMoney");
    }
}
