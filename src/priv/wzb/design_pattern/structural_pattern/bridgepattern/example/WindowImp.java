package priv.wzb.design_pattern.structural_pattern.bridgepattern.example;

/**
 * @author Satsuki
 * @time 2019/8/13 17:54
 * @description:
 * Windows操作系统实现类：具体实现类
 */
public class WindowImp implements ImageImp {
    @Override
    public void doPaint(Matrix m) {
        //调用Windows系统的绘制函数绘制像素矩阵
        System.out.println("在windows操作系统中显示图像");
    }
}
