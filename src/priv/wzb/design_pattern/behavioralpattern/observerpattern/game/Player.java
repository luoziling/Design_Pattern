package priv.wzb.design_pattern.behavioralpattern.observerpattern.game;

/**
 * @author Satsuki
 * @time 2019/4/7 14:30
 * @description:
 */

//战队成员类：具体观察者类
public class Player implements Observer {

    private String name;

    public Player(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void help() {
        System.out.println("坚持住，" + this.name + "来救你！");
    }

    @Override
    public void beAttacked(AllyControlCenter acc) {
        System.out.println(this.name + "被攻击！");
        acc.notifyObserver(name);

    }
}
