package priv.wzb.design_pattern.createdpattern.factorymethodpattern.classic;

/**
 * @author Satsuki
 * @time 2019/6/9 18:35
 * @description:
 */
public class ConcreteFactory implements Factory {
    @Override
    public Product factoryMethod() {
//        return new ConcreteProduct();
        return new Product() {
            @Override
            public void methodDiff() {

            }
        };
    }

    @Override
    public Product1 factoryMethod1() {
        return new ConcreteProduct1();
    }
}
