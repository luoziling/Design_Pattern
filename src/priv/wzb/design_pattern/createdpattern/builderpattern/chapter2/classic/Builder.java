package priv.wzb.design_pattern.createdpattern.builderpattern.chapter2.classic;

/**
 * @author Satsuki
 * @time 2019/6/25 15:54
 * @description:
 */
public abstract class Builder {
    //创建产品对象
    protected Product product = new Product();

    public abstract void buildPartA();
    public abstract void buildPartB();
    public abstract void buildPartC();
    //返回产品对象
    public Product getResult(){
        return product;
    }
}
