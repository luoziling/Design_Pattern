package priv.wzb.design_pattern.structural_pattern.bridgepattern.example;

/**
 * @author Satsuki
 * @time 2019/8/13 17:59
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Image image = new JPGImage();
        ImageImp imageImp = new WindowImp();
        image.setImageImp(imageImp);
        image.parseFile("satsuki");
    }
}
