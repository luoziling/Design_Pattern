package priv.wzb.leet_code.array.Maximum_Product_Subarray_152;

import java.util.Map;

/**
 * @author Satsuki
 * @time 2020/5/18 1:06
 * @description:
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 * 示例 2:
 *
 * 输入: [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-product-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution1 {
    public int maxProduct(int[] nums) {
        if (nums.length==0) return 0;
        // 初始化最大值
        int max = Integer.MIN_VALUE;
        // 记录两个
        // 状态转移方程需要根据正负来判断，如果数组中出现两个负数相乘仍是正数
        // 转移方程：max = (f(nums[i-1])max*nums[i])
        int imax = 1;
        int imin = 1;
        for (int i = 0; i < nums.length; i++) {
            // 遇到小于零的就交换
            if (nums[i]<0){
                int temp = imin;
                imin = imax;
                imax = temp;
            }
            // 更新imax和imin
            // 不管正负更新后的绝对值肯定最大
            imax = Math.max(imax * nums[i],nums[i]);
            imin = Math.min(imin* nums[i],nums[i]);
            max = Math.max(max,imax);
        }

        return max;
    }
}
