package priv.wzb.leet_code.dynamic_programming.longest_increasing_subsequence_300;

import java.util.Arrays;

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
 * 动态规划
 * 他的方法依赖于这样一个事实，在给定数组中最长上升子序列可能达到ith独立于
 * 后面在数组中出现的元素。
 */
public class Solution3 {
    public int lengthOfLIS(int[] nums) {
        if (nums.length==0){
            return 0;
        }
        // dp数组/动态规划数组 dp[i]代表了前i个元素的最大上升子序列的个数
        int[] dp = new int[nums.length];
        // 初始化第0个的数量为1
        dp[0] = 1;
        // 预先定义最长组序列长度为1
        int maxans = 1;
        for (int i = 1; i < dp.length; i++) {
            // 记录查找过程中某次的
            int maxval = 0;
            // 遍历i之前的所有元素
            for (int j = 0; j < i; j++) {
                // 在前面发小有比当前元素小的元素
                if (nums[i]>nums[j]){
                    maxval = Math.max(maxval,dp[j]);// 在这里就可以看出每次maxval也就是以每个元素为最后界限的最长升序子序长度，十分巧妙。可能等于每找到一个比当前元素小的就加一
                }
            }
            // 这里加一的原因在于之前初始化的dp都是0，但是实际上起码是1也就是本身必须包含进子序列，
            dp[i] = maxval+1;
            // 在查找每一个元素的最长升序子序列过程中记录最长的子序列
            maxans = Math.max(maxans,dp[i]);
        }
        return maxans;
    }

    public static void main(String[] args) {
        int[] a = new int[]{10,9,2,5,3,7,101,18};
        System.out.println(new Solution3().lengthOfLIS(a));
    }


}
