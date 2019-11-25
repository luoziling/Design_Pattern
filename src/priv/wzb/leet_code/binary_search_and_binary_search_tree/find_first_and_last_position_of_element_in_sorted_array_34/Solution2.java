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
public class Solution2 {
    // 找左侧的index
    int leftIndex(int[] nums,int target){
        int left = 0;
        int right = nums.length-1;
        while (left<=right){
//            int mid = left+(right-left)/2;
            int mid = (right+left)/2;
            // 找到了
            if (target == nums[mid]){
                // 如果是在最左侧或者左边一个数字比target小说明这就是左侧边界直接返回
                if (mid == 0 || nums[mid-1]<target){
                    return mid;
                }
                // 如果左侧还能找到继续向左找
                right = mid-1;
            }else if (target<nums[mid]){
                right = mid-1;
            }else if (target>nums[mid]){
                left = mid+1;
            }
        }
        return -1;
    }

    int rightIndex(int[] nums,int target){
        int left = 0;
        int right = nums.length-1;
        while (left<=right){
//            int mid = left+(right-left)/2;
            int mid = (right+left)/2;
            // 找到了
            if (target == nums[mid]){
                // 如果是在最右侧或者右边一个数字比target大说明这就是右侧边界直接返回
                if (mid == nums.length-1 || nums[mid+1]>target){
                    return mid;
                }
                // 如果右侧还能找到继续向右找
                left = mid+1;
            }else if (target<nums[mid]){
                right = mid-1;
            }else if (target>nums[mid]){
                left = mid+1;
            }
        }
        return -1;
    }
    public int[] searchRange(int[] nums, int target) {
//        int[] targetRange = {-1,-1};

        int left = leftIndex(nums,target);
        int right = rightIndex(nums,target);
        return new int[]{left,right};
    }


}
