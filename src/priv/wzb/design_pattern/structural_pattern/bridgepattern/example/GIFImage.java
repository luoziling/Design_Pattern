package priv.wzb.design_pattern.structural_pattern.bridgepattern.example;

/**
 * @author Satsuki
 * @time 2019/8/13 17:58
 * @description:
 * GIF格式图像：扩充抽象类
 */
public class GIFImage extends Image {
    public void parseFile(String fileName) {
        //模拟解析GIF文件并获得一个像素矩阵对象m;
        Matrix m = new Matrix();
        imageImp.doPaint(m);
        System.out.println(fileName + "，格式为GIF。");
    }
}
