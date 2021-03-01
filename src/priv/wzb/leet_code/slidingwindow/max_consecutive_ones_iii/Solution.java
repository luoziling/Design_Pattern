package priv.wzb.leet_code.slidingwindow.max_consecutive_ones_iii;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/19 22:23
 * @since 1.0.0
 */
public class Solution {
    public int longestOnes(int[] A, int K) {
        int N = A.length;
        int res = 0;
        int left = 0,right = 0;
        int zeros = 0;
        // 滑动窗口右侧不断向终点移动
        while (right< N){
            // 如果下一个是0则0的计数增加
            if (A[right] == 0){
                zeros++;
            }
            // 如果0的计数超过上限则移动滑动窗口左侧端点以找到合适的子序列
            while (zeros > K){
                if (A[left++] == 0){
                    zeros--;
                }
            }
            // 统计数组大小
            res = Math.max(res, right-left+1);
            // 右移
            right++;
        }
        return res;
    }
}
