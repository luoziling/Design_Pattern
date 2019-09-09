package priv.wzb.design_pattern.createdpattern.simplefactorypattern.classic;

/**
 * @author Satsuki
 * @time 2019/6/9 18:08
 * @description:
 */
public class Factory {
    //静态工厂方法
    public static Product getProduct(String arg){
        Product product = null;
        if(arg.equalsIgnoreCase("A")){
            product = new ConcreteProduct();
            //初始化设值product
        }
        else if (arg.equalsIgnoreCase("B")) {
//            product = new ConcreteProductB();
            //初始化设置product
        }
        return product;
    }
}
