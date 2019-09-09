package priv.wzb.design_pattern.structural_pattern.flyweightpattern.example;

/**
 * @author Satsuki
 * @time 2019/6/18 17:24
 * @description:围棋棋子类，抽象享元类
 */
public abstract class IgoChessman {
    public abstract String getColor();
    public void display(){
        System.out.println("棋子颜色" + this.getColor());
    }
}
