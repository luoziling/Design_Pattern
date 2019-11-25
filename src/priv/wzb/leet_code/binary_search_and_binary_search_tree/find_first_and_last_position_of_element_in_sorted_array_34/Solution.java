package priv.wzb.leet_code.binary_search_and_binary_search_tree.find_first_and_last_position_of_element_in_sorted_array_34;

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
 * 线性查找
 * 从左向右找到第一次出现的左端点
 * 从右向左找到第一次出现的右端点
 */
public class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] targetRange = {-1,-1};

        // 找到目标的左端点
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target){
                targetRange[0] = i;
                break;
            }
        }

        // 如果未找到直接返回
        if (targetRange[0] == -1){
            return targetRange;
        }

        // 从右向左找，找右端点也是第一次出现的下标
        for (int i = nums.length-1; i >= 0; i--) {
            if (nums[i] == target){
                targetRange[1] = i;
                break;
            }
        }
        return targetRange;
    }
}
