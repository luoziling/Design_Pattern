package priv.wzb.design_pattern.behavioralpattern.strategy_pattern.example_cinema;

/**
 * @author Satsuki
 * @time 2019/12/16 0:40
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        MovieTicket mt = new MovieTicket();
        Discount discount;
        double originalPrice = 60.0;
        double currentPrice;

        mt.setPrice(originalPrice);

        // 假设是学生（具体应该通过xml文件进行设定通过xml工具类进行获取再通过Java反射机制动态构造具体折扣类型
        System.out.println("原价:" + originalPrice);
        System.out.println("----------------------");
        discount = new StudentDiscount();
        mt.setDiscount(discount);
        currentPrice = mt.getPrice();
        System.out.println("现价:" + currentPrice);
    }
}
