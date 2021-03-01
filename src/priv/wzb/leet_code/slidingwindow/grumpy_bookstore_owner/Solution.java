package priv.wzb.leet_code.slidingwindow.grumpy_bookstore_owner;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/23 21:47
 * @since 1.0.0
 */
public class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int left,right = 0;
        int n = customers.length;
        int res = 0,sum = 0;
        // 计算不生气时的收益
        for (int i = 0; i < n; i++) {
            if (grumpy[i] ==0){
                res += customers[i];
            }
        }
        // 计算滑动窗口
        // 计算生气时赶走的总客户，滑动窗口固定长度3并且从最左侧开始
        for (int i = 0; i < X; i++) {
            sum += customers[i] * grumpy[i];
        }
        int maxSum = sum;
        right = X;
        while (right<n){
            // 右移过程中不断增加不生气带来的收益
            sum = sum-customers[right-X]*grumpy[right-X] + customers[right]*grumpy[right];
            maxSum = Math.max(maxSum,sum);
            right++;
        }
        return res + maxSum;
    }
}
