package priv.wzb.design_pattern.structural_pattern.proxypattern.staticproxy;

/**
 * @author Satsuki
 * @time 2019/8/12 16:05
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Star real = new RealStar();
        Star proxy = new ProxyStar(real);
        proxy.confer();
        proxy.signContract();
        proxy.bookTicket();
        proxy.sing();
        proxy.collectMoney();
    }
}
