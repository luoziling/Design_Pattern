package priv.wzb.design_pattern.structural_pattern.adapterpattern;

/**
 * @author Satsuki
 * @time 2019/5/4 21:16
 * @description:抽象成绩操作类：目标接口（Target）
 */
public interface ScoreOperation {
    public int[] sort(int array[]);//成绩排序
    public int search(int array[],int key);//成绩查找
}
