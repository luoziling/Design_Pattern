package priv.wzb.design_pattern.structural_pattern.bridgepattern.example;

/**
 * @author Satsuki
 * @time 2019/8/13 17:54
 * @description:
 * Linux操作系统实现类：具体实现类
 */
public class LinuxImp implements ImageImp {
    @Override
    public void doPaint(Matrix m) {
        //调用Linux系统的绘制函数绘制像素矩阵
        System.out.print("在Linux操作系统中显示图像：");

    }
}
