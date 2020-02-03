package priv.wzb.leet_code.dynamic_programming.maximum_subarray_53;

/**
 * @author Satsuki
 * @time 2019/12/2 20:51
 * @description:
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 示例:
 *
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 进阶:
 *
 * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 求数组中的最大连续子数组，分解为第i个和第i-1个
 * 边界：只有1个元素那么最大子序列就是这个数
 * 状态转化方程：dp[i] = Math.max(nums[i]+dp[i-1],nums[i]);
 * // 这里思考的解答未正确，，，
 */
public class Solution1 {
    public int maxSubArray(int[] nums) {
        // 动态规划
        if (nums.length == 0){
            return 0;
        }
        if(nums.length==1){
            return nums[0];
        }
        // 这里的动态规划数组中存储的不是最大值，而是某一个阶段的较大值
        int[] dp = new int[nums.length];
        dp[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i]>dp[i-1]){
                // 且当前值大于前i-1个之和
                dp[i] = nums[i];
            }else {
                // 否则还是要加入，因为是连续的
                dp[i] = dp[i-1]+nums[i];
            }
//            // 如果前面的和小于零
//            if (dp[i-1]<0){
//                if (nums[i]>dp[i-1]){
//                    // 且当前值大于前i-1个之和
//                    dp[i] = nums[i];
//                }else {
//                    // 否则还是要加入，因为是连续的
//                    dp[i] = dp[i-1]+nums[i];
//                }
//            }else {
//                // 前面和大于零
//                // 判定当前值是否大于零
//                if (nums[i]>0){
//                    // 大于零就把当前值加入
//                    dp[i] = dp[i-1]+nums[i];
//                }else {
//                    // 要的是连续所以在这里错误
//                    // 当前小于零就不加入
//                    dp[i] = dp[i-1];
//                }
//                // 必定要选择加入或者不加入的
////                if (nums[i]>dp[i-1]){
////                    // 与上面处理方法相同如果当前值大于前i-1个之和
////                    dp[i] = nums[i];
////                }else {
////                    dp[i]
////                }
//            }

        }
        // 返回最后一个
        return dp[dp.length-1];
    }

    public static void main(String[] args) {
        System.out.println(new Solution1().maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }

}
