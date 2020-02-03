package priv.wzb.design_pattern.behavioralpattern.strategy_pattern.classic;

/**
 * @author Satsuki
 * @time 2019/12/16 0:29
 * @description:
 */
public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        AbstractStrategy strategy;
        // 可在运行时指定类型，通过配置文件和反射机制实现
        strategy = new ConcreteStrategyA();
        context.setStrategy(strategy);
        context.algorithm();
    }
}
