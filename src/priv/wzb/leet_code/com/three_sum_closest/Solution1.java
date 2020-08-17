package priv.wzb.leet_code.com.three_sum_closest;

import java.util.Arrays;

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
 */
public class Solution1 {
    public int threeSumClosest(int[] nums, int target) {
        // 首先将数组排序
        // 排序
        Arrays.sort(nums);
        // 接近程度
        int cloest = Integer.MAX_VALUE;
        // start指向i+1的位置 end指向最后的位置
        int start=0,end=0;
        int sum = 0;
        int reSum = 0;
        for (int i = 0; i < nums.length; i++) {
            start = i+1;
            end = nums.length-1;
            while (start<end){
                sum = nums[i] + nums[start] + nums[end];
                // 差距
                int abs = Math.abs(target - sum);
                if (abs<cloest){
                    cloest = abs;
                    reSum = sum;
                }
                if (sum>target){
                    end--;
                }else if(sum==target){
                    return sum;
                }else {
                    start++;
                }
            }

        }
        return reSum;
    }

    public static void main(String[] args) {
        int a[] = {-1,2,1,-4};
        System.out.println(new Solution1().threeSumClosest(a,1));
    }
}
