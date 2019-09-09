package priv.wzb.design_pattern.createdpattern.factorymethodpattern.classic;

/**
 * @author Satsuki
 * @time 2019/6/9 18:02
 * @description:抽象产品类
 */
public abstract class Product {
    //所有产品类的公共业务方法
    public void methodSame(){
        //公共方法的实现
    }

    //声明抽象业务方法
    public abstract void methodDiff();
}
