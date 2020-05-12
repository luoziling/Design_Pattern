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
public class Solution {
    public double quickMul(double x,long N){
        // 递归退出条件，如果分解到最后=0则返回1（乘1等于自身
        if (N==0){
            return 1.0;
        }
        // 递归分解，二分，不断拆分N比循环一次一次要快很多
        double y = quickMul(x,N/2);
        // 计算结果，在拆分完后计算，根据中间过程为奇偶进行分开判断
        // 奇数（N）个x就前面两次相乘外要额外乘一个x
        // 偶数个就直接是前面两次之乘积
        // 例如x^32= x^16*x^16而x^33=x^16*x^16*x
        return N%2 == 0 ? y*y:y*y*x;
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
