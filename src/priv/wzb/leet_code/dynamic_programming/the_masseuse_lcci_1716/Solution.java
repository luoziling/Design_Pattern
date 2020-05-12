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
public class Solution {
    public int massage(int[] nums) {
        // 只有一个元素就选它
        // 两个元素取较大的
        // 三个元素取13或者2
        // 四个元素取13或者24
        //
        // 最大时长
        int maxTime = 0;
        // 选取的当前时长
        int nowTime = 0;
        // 遍历所有可能取最大
        for (int i = 0; i < nums.length; i++) {
            nowTime = nums[i];
            nowTime += length(nums,i);
            if (nowTime>maxTime){
                maxTime=nowTime;
            }
        }

        return maxTime;

    }

    /**
     *
     * @param x 从第几个开始
     * @param nums 原数组
     * @return 选择当前元素条件下的距离
     */
    private int length(int[] nums,int x){
        for (int i = x; i < nums.length; i++) {

        }
        return 0;
    }

}
