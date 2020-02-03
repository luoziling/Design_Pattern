package priv.wzb.design_pattern.behavioralpattern.strategy_pattern.example_cinema;

/**
 * @author Satsuki
 * @time 2019/12/16 0:34
 * @description: 环境类
 */
public class MovieTicket {
    private double price;
    private Discount discount;

    public void setPrice(double price){
        this.price = price;
    }

    // 注入一个折扣类
    public void setDiscount(Discount discount){
        this.discount = discount;
    }

    public double getPrice(){
        // 计算具体打折后的价格
        return discount.calculate(price);
    }
}
