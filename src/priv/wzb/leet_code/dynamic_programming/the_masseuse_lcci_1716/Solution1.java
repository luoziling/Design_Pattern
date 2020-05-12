package priv.wzb.leet_code.dynamic_programming.the_masseuse_lcci_1716;

/**
 * @author Satsuki
 * @time 2020/3/24 15:48
 * @description:
 * 一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。
 *
 * 注意：本题相对原题稍作改动
 *
 *  
 *
 * 示例 1：
 *
 * 输入： [1,2,3,1]
 * 输出： 4
 * 解释： 选择 1 号预约和 3 号预约，总时长 = 1 + 3 = 4。
 * 示例 2：
 *
 * 输入： [2,7,9,3,1]
 * 输出： 12
 * 解释： 选择 1 号预约、 3 号预约和 5 号预约，总时长 = 2 + 9 + 1 = 12。
 * 示例 3：
 *
 * 输入： [2,1,4,5,3,1,1,3]
 * 输出： 12
 * 解释： 选择 1 号预约、 3 号预约、 5 号预约和 8 号预约，总时长 = 2 + 4 + 3 + 3 = 12。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/the-masseuse-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 原问题：
 * 现问题：遍历数列，在相邻元素不可同时取的前提下找出最大序列
 * 边界：
 * 动态规划公式dp[i][j]=
 */
public class Solution1 {
    public int massage(int[] nums) {
        int n = nums.length;
        if (n==0){
            return 0;
        }
        // dp代表动态规划
        int dp0=0,dp1=nums[0];
        for (int i = 1; i < n; i++) {
            // 每一个数字之和它的前一个数字有关
            // 0代表不预约1代表预约
            // 不预约的话就说明不管前一个预约或者不预约都可以因为不选所以不用加当前数值
            // 所以取较大值
            int tdp0=Math.max(dp0,dp1);
            // 预约的话就说明前一个不预约，加上现在的值就可以
            int tdp1=dp0+nums[i];

            dp0=tdp0;
            dp1=tdp1;
        }
        return Math.max(dp0,dp1);

    }


}
