package priv.wzb.design_pattern.createdpattern.builderpattern.example;

/**
 * @author Satsuki
 * @time 2019/6/25 16:06
 * @description:
 */
public class DevilBuilder extends ActorBuilder {
    public  void buildType()
    {
        actor.setType("恶魔");
    }
    public  void buildSex()
    {
        actor.setSex("妖");
    }
    public  void buildFace()
    {
        actor.setFace("丑陋");
    }
    public  void buildCostume()
    {
        actor.setCostume("黑衣");
    }
    public  void buildHairstyle()
    {
        actor.setHairstyle("光头");
    }
}
