package priv.wzb.design_pattern.behavioralpattern.observerpattern.game;

import java.util.ArrayList;

/**
 * @author Satsuki
 * @time 2019/4/7 14:32
 * @description:
 */
abstract class AllyControlCenter {
    //战队名称
    protected String allyName;
    protected ArrayList<Observer> players = new ArrayList<>();

    public String getAllyName() {
        return allyName;
    }

    public void setAllyName(String allyName) {
        this.allyName = allyName;
    }

    //注册方法
    public void join(Observer obs){
        System.out.println(obs.getName() + "加入" + this.allyName + "战队！");
        players.add(obs);
    }

    //注销方法
    public void quit(Observer obs){
        System.out.println(obs.getName() + "退出" + this.allyName + "战队！");
        players.remove(obs);
    }

    //声明抽象通知方法
    public abstract void notifyObserver(String name);

}
