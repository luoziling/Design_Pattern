package priv.wzb.interview.nowcoder;

/**
 * @author Satsuki
 * @time 2019/10/26 21:51
 * @description:
 * 有1分，2分，5分，10分四种硬币，
 * 每种硬币数量无限，给定n分钱(n <= 100000)，有多少中组合可以组成n分钱？
 */
public class Circum {

    public static int count(int money){
        int count=0;
        int a = 1;
        int b = 2;
        int c = 5;
        int d = 10;
        int x=0;
        for (int i = 0; i <= 100000; i++) {
            for (int j = 0; j <= 50000; j++) {
                for (int k = 0; k <= 20000; k++) {
                    for (int l = 0; l <= 10000; l++) {
                        x = a*i + b*j + c*k + d*l;
                        if (x == money){
                            System.out.println(x);
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(count(12345));
    }
}
