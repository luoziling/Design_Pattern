package priv.wzb.design_pattern.behavioralpattern.statepattern.example;

/**
 * @author Satsuki
 * @time 2020/6/20 22:42
 * @description:
 */
public class Client {
    public static void main(String args[]) {
        Account acc = new Account("段誉",0.0);
        acc.deposit(1000);
        acc.withdraw(2000);
        acc.deposit(3000);
        acc.withdraw(4000);
        acc.withdraw(1000);
        acc.computeInterest();
    }
}
