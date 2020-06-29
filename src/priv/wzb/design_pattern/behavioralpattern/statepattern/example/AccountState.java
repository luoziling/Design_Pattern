package priv.wzb.design_pattern.behavioralpattern.statepattern.example;

/**
 * @author Satsuki
 * @time 2020/6/20 22:26
 * @description:
 * 抽象状态类
 * 三种状态
 */
public abstract class AccountState {
    protected Account acc;
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract void computeInterest();
    public abstract void stateCheck();
}
