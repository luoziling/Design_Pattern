package priv.wzb.leet_code.stack_queue_heap.kth_largest_element_in_an_array_215;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/10 18:19
 * @description:
 * 暴力解法
 */
public class Solution {
    public int findKthLargest(int[] nums, int k) {
        // 排序
        Arrays.sort(nums);
        // 返回
        return nums[nums.length-k];
    }
}
