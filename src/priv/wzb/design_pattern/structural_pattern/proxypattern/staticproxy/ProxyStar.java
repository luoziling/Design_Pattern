package priv.wzb.design_pattern.structural_pattern.proxypattern.staticproxy;

/**
 * @author Satsuki
 * @time 2019/8/12 16:00
 * @description:
 */
public class ProxyStar implements Star {
    private Star realStar;

    ProxyStar(Star realStar){
        this.realStar = realStar;
    }

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
        realStar.sing();
    }

    @Override
    public void collectMoney() {
        System.out.println("collectMoney");
    }
}
