package priv.wzb.design_pattern.structural_pattern.bridgepattern.example;

/**
 * @author Satsuki
 * @time 2019/8/13 17:57
 * @description:/
 * NG格式图像：扩充抽象类
 */
public class PNGImage extends Image {
    public void parseFile(String fileName) {
        //模拟解析PNG文件并获得一个像素矩阵对象m;
        Matrix m = new Matrix();
        imageImp.doPaint(m);
        System.out.println(fileName + "，格式为PNG。");
    }
}
