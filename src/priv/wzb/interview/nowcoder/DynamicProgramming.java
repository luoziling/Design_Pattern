package priv.wzb.interview.nowcoder;

import java.util.Scanner;

/**
 * @author Satsuki
 * @time 2019/10/26 23:24
 * @description:
 * 有1分，2分，5分，10分四种硬币，
 * 每种硬币数量无限，给定n分钱(n <= 100000)，有多少中组合可以组成n分钱？
 */
public class DynamicProgramming {
    int count=0;
    int[] arr;


    public int calculateWays(int n) {
        arr = new int[n + 1];
        return calculateWays1(n);
    }

    public int calculateWays1(int n){
        if (n<0)
            throw new IllegalArgumentException("input wrong");
        if(n==0){
            return 0;
        }
        if (n==1)
            return 1;

        if (n == 2 || n == 3)
            return 2;
        if (n == 4 || n == 5 || n == 6 || n == 7 || n == 8 || n == 9)
            return n - 1;
        if (n == 10)
            return 11;
        if (arr[n] != 0)
            return arr[n];

        int res = 0;
//        res = Math.max(Math.max(calculateWays1(n-1)+1,calculateWays1(n-2)+2),Math.max(calculateWays1(n-5)+4,calculateWays1(n-10)+11));

                res = Math.max(Math.max(calculateWays1(n - 1) + 1, calculateWays1(n - 2) + 2), Math.max(calculateWays1(n - 5) + 4, calculateWays1(n - 10) + 11));
        arr[n] = res;
        return res;
    }

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        System.out.println("请输入钱数：");
        int n = sc.nextInt();
        DynamicProgramming s = new DynamicProgramming();
        int sum = s.calculateWays(n);
        System.out.println(sum);
    }

}
