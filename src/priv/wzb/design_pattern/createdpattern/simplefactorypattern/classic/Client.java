package priv.wzb.design_pattern.createdpattern.simplefactorypattern.classic;

/**
 * @author Satsuki
 * @time 2019/6/9 18:10
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Product product;
        product = Factory.getProduct("A");//通过工厂类创建产品对象
        product.methodSame();
        product.methodDiff();
    }
}
