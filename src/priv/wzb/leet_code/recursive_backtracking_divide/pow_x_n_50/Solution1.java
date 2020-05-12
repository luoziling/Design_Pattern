package priv.wzb.leet_code.recursive_backtracking_divide.pow_x_n_50;

/**
 * @author Satsuki
 * @time 2020/5/11 21:48
 * @description:
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
 *
 * 示例 1:
 *
 * 输入: 2.00000, 10
 * 输出: 1024.00000
 * 示例 2:
 *
 * 输入: 2.10000, 3
 * 输出: 9.26100
 * 示例 3:
 *
 * 输入: 2.00000, -2
 * 输出: 0.25000
 * 解释: 2-2 = 1/22 = 1/4 = 0.25
 * 说明:
 *
 * -100.0 < x < 100.0
 * n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1] 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/powx-n
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution1 {
    public double quickMul(double x,long N){
        // 快速幂+迭代
        double ans = 1.0;
        double x_contribute=x;
        // 在对N进行二进制拆分的同时计算答案
        while (N>0){
            if (N%2==1){
                // 如果N二进制表示的最低位为1，那么需要计入贡献
                ans *= x_contribute;
            }
            // 将贡献不断平方
            x_contribute *=x_contribute;
            // 设期N二进制表示的最低为，这样每次值判断最低为即可
            N/=2;
        }
        return ans;
    }
    public double myPow(double x, int n) {
        long N = n;
        // 考虑n的正负（结果与结果的倒数
        return N>=0? quickMul(x,N): 1.0/quickMul(x,-N);
    }
//    public double myPow(double x, int n) {
//        double ans = 1;
//        int n1 = n;
//        // n为正数
//        if (n<0){
//            n1=-n;
//        }
//        for (int i = 0; i < n1; i++) {
//            ans*=x;
//        }
//
//        if (n<0){
//            return 1.0/ans;
//        }
//        return ans;
//    }

}
