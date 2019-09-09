package priv.wzb.design_pattern.structural_pattern.flyweightpattern.classic;

/**
 * @author Satsuki
 * @time 2019/6/18 17:10
 * @description:
 */
public class ConcreteFlyweight extends Flyweight {
    private String intrinsicState;

    public ConcreteFlyweight() {
    }

    public ConcreteFlyweight(String intrinsicState) {
        this.intrinsicState = intrinsicState;
    }

    //外部状态extrinsicState在使用时由外部设置，不保存在享元对象中，即使是同一对象
    //在每一次调用时可以传入不同的外部状态
    @Override
    public void operation(String extrinsicState) {
        //业务逻辑
    }
}
