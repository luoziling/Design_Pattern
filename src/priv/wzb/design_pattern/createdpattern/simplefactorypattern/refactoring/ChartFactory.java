package priv.wzb.design_pattern.createdpattern.simplefactorypattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 18:20
 * @description:图表工厂类：工厂类
 */
public class ChartFactory {
    //静态工厂方法
    public static Chart getChart(String type){
        Chart chart = null;
        if(type.equalsIgnoreCase("histogram")){
            chart = new HistogramChart();
            System.out.println("初始化设值柱状图");
        }
        else if (type.equalsIgnoreCase("pie")) {
            chart = new PieChart();
            System.out.println("初始化设置饼状图！");
        }
        else if (type.equalsIgnoreCase("line")) {
            chart = new LineChart();
            System.out.println("初始化设置折线图！");
        }
        return chart;
    }
}
