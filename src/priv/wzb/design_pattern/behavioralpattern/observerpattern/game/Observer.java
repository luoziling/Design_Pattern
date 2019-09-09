package priv.wzb.design_pattern.behavioralpattern.observerpattern.game;

/**
 * @author Satsuki
 * @time 2019/4/7 14:28
 * @description:
 */
public interface Observer {
    public String getName();
    public void setName(String name);
    public void help();//声明支援盟友
    //声明遭受攻击
    public void beAttacked(AllyControlCenter acc);
}
