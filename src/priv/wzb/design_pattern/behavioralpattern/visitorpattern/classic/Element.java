package priv.wzb.design_pattern.behavioralpattern.visitorpattern.classic;

/**
 * @author Satsuki
 * @time 2019/6/9 19:37
 * @description:
 */
public interface Element {
    public void accept(Visitor visitor);
}
