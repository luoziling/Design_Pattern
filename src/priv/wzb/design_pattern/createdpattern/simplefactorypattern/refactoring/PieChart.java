package priv.wzb.design_pattern.createdpattern.simplefactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 18:17
 * @description:饼图：具体产品类
 */
public class PieChart implements Chart{
    public PieChart() {
        System.out.println("创建饼图");
    }

    @Override
    public void display() {
        System.out.println("显示饼图");
    }
}
