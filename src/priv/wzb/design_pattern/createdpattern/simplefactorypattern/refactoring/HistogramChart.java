package priv.wzb.design_pattern.createdpattern.simplefactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 18:16
 * @description:
 */
public class HistogramChart implements Chart {

    public HistogramChart() {
        System.out.println("创建柱状图");
    }

    @Override
    public void display() {
        System.out.println("显示柱状图");
    }
}
