package priv.wzb.leet_code.binary_search_and_binary_search_tree.search_insert_position_35;

/**
 * @author Satsuki
 * @time 2019/11/24 16:32
 * @description:
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 *
 * 你可以假设数组中无重复元素。
 *
 * 示例 1:
 *
 * 输入: [1,3,5,6], 5
 * 输出: 2
 * 示例 2:
 *
 * 输入: [1,3,5,6], 2
 * 输出: 1
 * 示例 3:
 *
 * 输入: [1,3,5,6], 7
 * 输出: 4
 * 示例 4:
 *
 * 输入: [1,3,5,6], 0
 * 输出: 0
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-insert-position
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 就是对二分查找的修改
 */
public class Solution {
    public int searchInsert(int[] nums, int target) {
        // 起始与结尾
        int begin = 0;
        int end = nums.length-1;
        // 要返回的下标
        int index = -1;
//        while (begin<end){
        while (begin<=end){
        // 未找到应该在的位置
//        while (index == -1){
            // 中间值
//            int mid = (begin+end)/2;
            int mid = begin + (end-begin)/2;
            // 如果找到
            if (nums[mid] == target){
                index = mid;
                break;
            }else if (target<nums[mid]){
                // 在左侧
                // 如果找到了最左侧或者如果比mid小，比mid-1大那么就应该放在mid的位置
                if (mid==0||target>nums[mid-1]){
                    index = mid;
                }
                end = mid-1;
            }else if (target>nums[mid]){
                // 在右侧
                // 如果找到了最右侧或者如果比mid大，比mid+1小那么就应该放在mid+1的位置
                if (mid == nums.length-1||target<nums[mid+1]){
                    index = mid+1;
                }
                begin = mid+1;
            }
        }


        return index;


    }
}
