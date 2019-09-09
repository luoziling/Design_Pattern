package priv.wzb.design_pattern.createdpattern.builderpattern.chapter2.classic;

/**
 * @author Satsuki
 * @time 2019/6/25 15:58
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Builder builder = null;
//        builder = new ConcreteBuilder():
        Director director = new Director(builder);
        Product product = director.construct();
    }
}
