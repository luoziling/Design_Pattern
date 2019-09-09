package priv.wzb.design_pattern.structural_pattern.flyweightpattern.example;

/**
 * @author Satsuki
 * @time 2019/6/18 17:30
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        IgoChessman black1,black2,black3,white1,white2;
        IgoChessmanFactory factory;

        factory = IgoChessmanFactory.getInstance();

        black1 = factory.getIgoChessman("b");
        black2 = factory.getIgoChessman("b");
        black3 = factory.getIgoChessman("b");
        System.out.println("b1与b2是否为同一对象" + (black1.equals(black2)));

        white1 = factory.getIgoChessman("w");
        white2 = factory.getIgoChessman("w");
        System.out.println("w1 and w2" + (white1.equals(white2)));

        //display
        black1.display();
        black2.display();
        black3.display();
        white1.display();
        white2.display();
    }
}
