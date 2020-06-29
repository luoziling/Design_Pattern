package priv.wzb.design_pattern.behavioralpattern.statepattern.example;

/**
 * @author Satsuki
 * @time 2020/6/20 22:39
 * @description:
 * 透支状态：具体状态类
 */
public class OverdraftState extends AccountState {

    public OverdraftState(AccountState accountState) {
        this.acc = accountState.acc;
    }

    @Override
    public void deposit(double amount) {
        acc.setBalance(acc.getBalance() + amount);
        stateCheck();
    }

    @Override
    public void withdraw(double amount) {
        acc.setBalance(acc.getBalance() - amount);
        stateCheck();
    }

    @Override
    public void computeInterest() {
        System.out.println("计算利息！");
    }

    @Override
    public void stateCheck() {
        if (acc.getBalance() > 0) {
            acc.setState(new NormalState(this));
        }
        else if (acc.getBalance() == -2000) {
            acc.setState(new RestrictedState(this));
        }
        else if (acc.getBalance() < -2000) {
            System.out.println("操作受限！");
        }
    }
}
