package priv.wzb.design_pattern.structural_pattern.compositepattern.proto;

/**
 * @author Satsuki
 * @time 2019/5/6 22:51
 * @description:图像文件夹
 */
public class ImageFile {
    private String name;
    public ImageFile(String name){
        this.name = name;
    }

    public void killVirus(){
        //简化代码，模拟杀毒
        System.out.println("----对图像文件" + name + "进行杀毒");
    }
}
