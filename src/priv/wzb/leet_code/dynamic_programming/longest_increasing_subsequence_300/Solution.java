package priv.wzb.leet_code.dynamic_programming.longest_increasing_subsequence_300;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/12/3 21:04
 * @description:
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 *
 * 示例:
 *
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 * 说明:
 *
 * 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
 * 你算法的时间复杂度应该为 O(n2) 。
 * 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 利用动态规划
 * 原问题：求数组的最长上升子序列可以不连续
 * 子问题：数组每个元素的最长上升子序列
 * dp数组代表每个元素的最长上升子序列
 * 转换方程：按序遍历每个元素之前的所有元素找到小于当前元素且dp[i]<dp[j]+1
 */
public class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0){
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int LIS= 1;
        for (int i = 1; i < nums.length; i++) {
            // 初始化dp[i]
            dp[i] = 1;
            // 搜索前面元素
            for (int j = 0; j < i; j++) {
                // dp[i]<dp[j]+1代表了越前面的元素所代表的最长上升子序列就越小
                // 后续过程中的LIS数量最多比前面元素中最大的LIS大1
                if (nums[i]>nums[j] && dp[i]<dp[j]+1){
                    dp[i] = dp[j]+1;
                }
            }
            if (LIS<dp[i]){
                LIS = dp[i];
            }
        }
        return LIS;
    }

    public static void main(String[] args) {
        int[] a = new int[]{10,9,2,5,3,7,101,18};
        System.out.println(new Solution().lengthOfLIS(a));
    }
}
