package priv.wzb.interview.interview_360_programming.gold;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Satsuki
 * @time 2019/11/11 17:20
 * @description:
 * A、B两伙马贼意外地在一片沙漠中发现了一处金矿，双方都想独占金矿，但各自的实力都不足以吞下对方，经过谈判后，双方同意用一个公平的方式来处理这片金矿。处理的规则如下：他们把整个金矿分成n段，由A、B开始轮流从最左端或最右端占据一段，直到分完为止。
 *
 * 马贼A想提前知道他们能分到多少金子，因此请你帮忙计算他们最后各自拥有多少金子?（两伙马贼均会采取对己方有利的策略）
 *
 *
 *
 * 输入
 * 测试数据包含多组输入数据。输入数据的第一行为一个正整数T(T<=20)，表示测试数据的组数。然后是T组测试数据，每组测试数据的第一行包含一个整数n，下一行包含n个数（n <= 500 ），表示每段金矿的含金量，保证其数值大小不超过1000。
 *
 *
 *
 *
 * 样例输入
 * 2
 *
 * 6
 *
 * 4 7 2 9 5 2
 *
 * 10
 *
 * 140 649 340 982 105 86 56 610 340 879
 *
 *
 *
 *
 * 输出
 * 对于每一组测试数据，输出一行"Case #id: sc1 sc2"，表示第id组数据时马贼A分到金子数量为sc1，马贼B分到金子数量为sc2。详见样例。
 *
 *
 *
 *
 * 样例输出
 * Case #1: 18 11
 *
 * Case #2: 3206 981
 *
 *
 *
 *
 * 时间限制
 * C/C++语言：1000MS
 *
 * 其他语言：3000MS
 *
 * 内存限制
 * C/C++语言：65536KB
 *
 * 其他语言：589824KB
 */
public class DivideGold1 {

    public static void main(String[] args) {
        new DivideGold1().divide();
//        ArrayList
    }

    public void divide(){
        Scanner in = new Scanner(System.in);
        // num代表有几个案例
        int num = in.nextInt();
        // 动态规划问题都是从1开始到n一共n个数字
        // 通过画表格的方式解决问题
        for (int i = 1; i <= num; i++) {
            // 每个案例中有多少个数字
            int n = in.nextInt();
            // 记录n份金子
            int[] array = new int[n+1];
            // i分金子的数量综合
            int[] sum = new int[n+1];
            array[0] = 0;
            sum[0] = 0;
            // 从这里才正式开始输入
            for (int j = 1; j <= n; j++) {
                array[j] = in.nextInt();
                sum[j] = sum[j-1]+array[j];
            }
            // 动态规划数组
            // f更确切的来说是dp
            int[][] f = new int[n+1][n+1];
            for (int j = 1; j <= n; j++) {
                // 记录对角线
                f[j][j] = array[j];
            }
            int k = 1;
            while (k<= n-1) {
                // 从对角线元素开始计算，向右上挪动直至计算到f[1][n]
                for (int j = 1; j + k <= n; j++) {
                    f[j][j + k] = sum[j + k] - sum[j - 1] - Math.min(f[j][j + k - 1], f[j + 1][j + k]);
                }
                k++;
            }
            System.out.println("Case #"+i+": "+f[1][n]+" "+(sum[n]-f[1][n]));
        }
        in.close();
    }




}
