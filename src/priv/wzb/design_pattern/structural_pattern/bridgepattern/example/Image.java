package priv.wzb.design_pattern.structural_pattern.bridgepattern.example;

/**
 * @author Satsuki
 * @time 2019/8/13 17:51
 * @description:
 * 抽象图像类：抽象类
 */
public abstract class Image {
    protected ImageImp imageImp;


    public void setImageImp(ImageImp imageImp) {
        this.imageImp = imageImp;
    }

    public abstract void parseFile(String fileName);
}
