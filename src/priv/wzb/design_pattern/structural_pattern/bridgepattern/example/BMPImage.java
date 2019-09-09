package priv.wzb.design_pattern.structural_pattern.bridgepattern.example;

/**
 * @author Satsuki
 * @time 2019/8/13 17:58
 * @description:
 * BMP格式图像：扩充抽象类
 */
public class BMPImage extends Image {
    public void parseFile(String fileName) {
        //模拟解析BMP文件并获得一个像素矩阵对象m;
        Matrix m = new Matrix();
        imageImp.doPaint(m);
        System.out.println(fileName + "，格式为BMP。");
    }
}
