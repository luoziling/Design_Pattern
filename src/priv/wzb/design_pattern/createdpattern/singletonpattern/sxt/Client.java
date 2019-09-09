package priv.wzb.design_pattern.createdpattern.singletonpattern.sxt;

/**
 * @author Satsuki
 * @time 2019/8/18 16:23
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        SingleTon singleTon = SingleTon.getInstance();
        SingleTon singleTon2 = SingleTon.getInstance();
        System.out.println(singleTon==singleTon2);


    }
}
