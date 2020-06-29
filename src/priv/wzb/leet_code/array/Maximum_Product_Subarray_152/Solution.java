package priv.wzb.leet_code.array.Maximum_Product_Subarray_152;

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
public class Solution {
    public int maxProduct(int[] nums) {
        if (nums.length==0) return 0;
        int max = Integer.MIN_VALUE;
        int temp = 1;
        for (int i = 0; i < nums.length; i++) {
            // 初始化
            temp = 1;
            for (int j = i; j < nums.length; j++) {
                temp*=nums[j];
                if (temp>max){
                    max = temp;
                }
            }
        }

        return max;
    }
}
