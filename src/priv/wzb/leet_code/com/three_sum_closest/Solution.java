package priv.wzb.leet_code.com.three_sum_closest;

/**
 * @author Satsuki
 * @time 2019/11/9 20:49
 * @description:
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 *
 * 例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
 *
 * 与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/3sum-closest
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 暴力求解
 * 时间复杂度O(n的三次方)
 */
public class Solution {
    public int threeSumClosest(int[] nums, int target) {
        // 接近程度
        int cloest = Integer.MAX_VALUE;
        int fir=0,sec=0,third=0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                for (int k = j+1; k < nums.length; k++) {
                    int abs = Math.abs(target - nums[i] - nums[j] - nums[k]);
                    if (abs<cloest){
                        cloest = abs;
                        fir = nums[i];
                        sec = nums[j];
                        third = nums[k];
                    }
                }
            }


        }
        return fir+sec+third;
    }

    public static void main(String[] args) {

    }
}
