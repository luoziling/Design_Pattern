package priv.wzb.design_pattern.createdpattern.simplefactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 18:19
 * @description:
 */
public class LineChart implements Chart {
    public LineChart() {
        System.out.println("创建折线图");
    }

    @Override
    public void display() {
        System.out.println("显示折线图");
    }
}
