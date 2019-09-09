package priv.wzb.design_pattern.createdpattern.builderpattern.example;

/**
 * @author Satsuki
 * @time 2019/6/25 16:09
 * @description:
 */
public class client {
    public static void main(String[] args) {
        ActorBuilder ab;//针对抽象建造者编程
        ab = new AngleBuilder();
        ActorController ac = new ActorController();
        Actor actor;
        actor=ac.construct(ab);
        String  type = actor.getType();
        System.out.println(type  + "的外观：");
        System.out.println("性别：" + actor.getSex());
        System.out.println("面容：" + actor.getFace());
        System.out.println("服装：" + actor.getCostume());
        System.out.println("发型：" + actor.getHairstyle());
    }
}
