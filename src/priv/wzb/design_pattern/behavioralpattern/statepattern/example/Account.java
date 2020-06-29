package priv.wzb.design_pattern.behavioralpattern.statepattern.example;

/**
 * @author Satsuki
 * @time 2020/6/20 22:26
 * @description:
 *  银行账户，环境类
 */
public class Account {
    // 维持对抽象状态的引用
    private AccountState state;
    // 开户名
    private String owner;
    // 余额
    private double balance = 0;

    public Account(String owner,double balance){
        this.owner = owner;
        this.balance = balance;
        // 设置初始状态
        this.state = new NormalState(this);
        System.out.println(this.owner+"开户，金额："+balance);
        System.out.println("=================================");
    }


    public void setState(AccountState state) {
        this.state = state;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount){
        System.out.println(this.owner+"存款" + amount);
        state.deposit(amount);
        System.out.println("现在余额为"+ this.balance);
        System.out.println("现在帐户状态为"+ this.state.getClass().getName());
        System.out.println("---------------------------------------------");
    }

    public void withdraw(double amount){
        System.out.println(this.owner + "取款" + amount);
        state.withdraw(amount); //调用状态对象的withdraw()方法
        System.out.println("现在余额为"+ this.balance);
        System.out.println("现在帐户状态为"+ this. state.getClass().getName());
        System.out.println("---------------------------------------------");
    }

    public void computeInterest()
    {
        state.computeInterest(); //调用状态对象的computeInterest()方法
    }
}
