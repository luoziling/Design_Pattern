package priv.wzb.design_pattern.createdpattern.builderpattern.example;

/**
 * @author Satsuki
 * @time 2019/6/25 16:06
 * @description:游戏角色创建控制器：指挥者
 */
public class ActorController {
    //逐步构建复杂产品对象
    public Actor construct(ActorBuilder ab){
        Actor actor;
        ab.buildType();
        ab.buildSex();
        ab.buildFace();
        ab.buildCostume();
        ab.buildHairstyle();
        actor = ab.createActor();
        return actor;
    }
}
