package priv.wzb.design_pattern.dependency_inversion.weak;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-04 16:55
 **/

public class Bottom {
    private Tire tire;

    public Bottom(Tire tire) {
        this.tire = tire;
    }
}
