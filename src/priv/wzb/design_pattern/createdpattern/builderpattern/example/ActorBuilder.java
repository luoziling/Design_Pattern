package priv.wzb.design_pattern.createdpattern.builderpattern.example;

/**
 * @author Satsuki
 * @time 2019/6/25 16:01
 * @description:角色建造器：抽象建造者
 */
public abstract class ActorBuilder {
    protected Actor actor = new Actor();

    public abstract void buildType();
    public  abstract void buildSex();
    public  abstract void buildFace();
    public  abstract void buildCostume();
    public  abstract void buildHairstyle();

    //工厂方法,返回一个完整的游戏对象
    public Actor createActor(){
        return actor;
    }
}
