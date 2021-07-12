package priv.wzb.leet_code.dynamic_programming.offer_42;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/7/12 23:06
 * @description:
 * @since 1.0.0
 */
public class Solution {
    public int maxSubArray(int[] nums) {
        // dp[i]代表到目前为止的最大连续数组之和
        int[] dp = new int[nums.length];
        // 初始化
        dp[0] = nums[0];
        int ans = dp[0];
        for (int i = 1; i < nums.length; i++) {
            // 状态转移方程
            if (dp[i-1] <= 0){
                // 不管如何小于等于0的情况dp[i]就该被重置为nums[i]
                dp[i] = nums[i];
//                if (dp[i-1]<=nums[i]){
//                    dp[i] = nums[i];
//                }else {
//                    dp[i] = nums[i];
//                }

            }else {
                // 不管如何都应该加上当前，但dp[i]就不一定是最大，而是较大，因此需要比较
                // 因为求最大连续子数组所以要加上当前
                dp[i] = dp[i-1] + nums[i];
            }
            ans = Math.max(dp[i],ans);
        }
        return ans;

    }
}
