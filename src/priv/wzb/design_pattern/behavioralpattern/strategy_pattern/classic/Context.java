package priv.wzb.design_pattern.behavioralpattern.strategy_pattern.classic;

/**
 * @author Satsuki
 * @time 2019/12/16 0:26
 * @description: 环境类
 * 对于Context类而言，在它与抽象策略类之间建立一个关联关系
 */
public class Context {
    // 维持一个对抽象策略类的引用
    private AbstractStrategy strategy;

    public void setStrategy(AbstractStrategy strategy){
        this.strategy = strategy;
    }

    // 调用策略类中的算法
    public void algorithm(){
        strategy.algorithm();
    }
}
