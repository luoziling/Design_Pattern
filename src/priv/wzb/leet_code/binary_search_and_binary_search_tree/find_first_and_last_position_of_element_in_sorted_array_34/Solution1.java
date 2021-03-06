package priv.wzb.leet_code.binary_search_and_binary_search_tree.find_first_and_last_position_of_element_in_sorted_array_34;

import priv.wzb.interview.test.P;

/**
 * @author Satsuki
 * @time 2019/11/24 17:01
 * @description:
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 你的算法时间复杂度必须是 O(log n) 级别。
 *
 * 如果数组中不存在目标值，返回 [-1, -1]。
 *
 * 示例 1:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 8
 * 输出: [3,4]
 * 示例 2:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 6
 * 输出: [-1,-1]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution1 {
    /**
     * 经由二分查找返回最左或者最右的目标下标
     * @param nums
     * @param target
     * @param left
     * @return
     */
    private int extremeInsertionIndex(int[] nums,int target,boolean left){
        int lo = 0;
        int hi = nums.length;
        while (lo<hi){
            int mid = (lo+hi)/2;
            if (nums[mid]>target || (left&&target==nums[mid])) {
                hi = mid;
            }else {
                lo = mid+1;
            }
        }
        return lo;
    }
    public int[] searchRange(int[] nums, int target) {
        int[] targetRange = {-1,-1};

        int leftIdx = extremeInsertionIndex(nums,target,true);
        if (leftIdx == nums.length||nums[leftIdx] != target){
            return targetRange;
        }

        targetRange[0] = leftIdx;
        targetRange[1] = extremeInsertionIndex(nums,target,false)-1;

        return targetRange;
    }
}
