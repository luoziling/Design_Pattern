package priv.wzb.design_pattern.behavioralpattern.strategy_pattern.example_cinema;

/**
 * @author Satsuki
 * @time 2019/12/16 0:34
 * @description: 抽象策略类
 */
public interface Discount {
    double calculate(double price);
}
