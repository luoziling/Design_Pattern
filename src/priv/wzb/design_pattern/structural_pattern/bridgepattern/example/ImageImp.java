package priv.wzb.design_pattern.structural_pattern.bridgepattern.example;

/**
 * @author Satsuki
 * @time 2019/8/13 17:52
 * @description:
 * 抽象操作系统实现类：实现类接口
 */
public interface ImageImp {
    //显示像素矩阵m
    public void doPaint(Matrix m);
}
