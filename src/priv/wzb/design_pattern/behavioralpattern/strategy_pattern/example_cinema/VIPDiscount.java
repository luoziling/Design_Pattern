package priv.wzb.design_pattern.behavioralpattern.strategy_pattern.example_cinema;

/**
 * @author Satsuki
 * @time 2019/12/16 0:35
 * @description: 具体策略类
 */
public class VIPDiscount implements Discount{
    private final double DISCOUNT=0.5;
    @Override
    public double calculate(double price) {
        System.out.println("VIP票：");
        System.out.println("增加积分");
        return price*DISCOUNT;
    }
}
