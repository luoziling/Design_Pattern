package priv.wzb.design_pattern.behavioralpattern.visitorpattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 19:44
 * @description:
 */
public interface Employee {
    public void accept(Department handler);//接受一个抽象访问者访问
}
