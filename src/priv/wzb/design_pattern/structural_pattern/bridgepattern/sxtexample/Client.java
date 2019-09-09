package priv.wzb.design_pattern.structural_pattern.bridgepattern.sxtexample;

/**
 * @author Satsuki
 * @time 2019/8/13 17:14
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        //销售联想笔记本
        Computer computer = new Laptop(new Lenovo());
        computer.sale();

    }
}
