package priv.wzb.design_pattern.behavioralpattern.strategy_pattern.example_cinema;

/**
 * @author Satsuki
 * @time 2019/12/16 0:35
 * @description: 具体策略类
 */
public class ChildDiscount implements Discount{
    private final double DISCOUNT=10;
    @Override
    public double calculate(double price) {
        System.out.println("儿童票：");
        if (price>20){
            return price-DISCOUNT;
        }else {
            return price;
        }

    }
}
