package priv.wzb.design_pattern.createdpattern.simplefactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 18:22
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Chart chart;
        chart = ChartFactory.getChart("histogram");//通过静态工厂方法创建产品
        chart.display();
    }
}
