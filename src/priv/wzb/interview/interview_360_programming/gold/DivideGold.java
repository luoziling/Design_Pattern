package priv.wzb.interview.interview_360_programming.gold;

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
public class DivideGold {

    public static void main(String[] args) {
        // 金矿
        int[] gold = {4,7,2,9,5,2};
        // 应该能拿到的最大值
        int sc1= 0,sc2 = 0;
        int adp[] = new int[(int)Math.ceil(gold.length/2.0)];
        int bdp[] = new int[(int)Math.ceil(gold.length/2.0)];
        // 中间数
        int a=0,b=0;
        // 初始下表
        int j = 0;
        // 最后下标
        int k = gold.length-1;
        for (int i = 0; i < gold.length; i++) {
            // 先从左边拿
            a+=gold[i];
            // 假设另一个总是去左右最大的（贪心
            // j记录拿掉左边之后的数组初始
            j = i+1;
            // 第二队拿左右中较大的
            if (gold[j]>=gold[k]){
                // 左边大于等于右边，选左边
                b+=gold[j];
                j++;
            }else {
                // 拿右边
                b+=gold[k];
                k++;
            }
            // 如果左边


        }
    }


}
