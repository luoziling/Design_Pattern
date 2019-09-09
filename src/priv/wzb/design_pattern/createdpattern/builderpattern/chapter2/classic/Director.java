package priv.wzb.design_pattern.createdpattern.builderpattern.chapter2.classic;

/**
 * @author Satsuki
 * @time 2019/6/25 15:57
 * @description:
 */
public class Director {
    private Builder builder;
    public Director(Builder builder){
        this.builder = builder;
    }
    public void setBuilder(Builder builder){
        this.builder = builder;
    }

    //产品构建与组装
    public Product construct(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}
