package priv.wzb.leet_code.slidingwindow.longest_continuous_subarray_with_absolute_diff_less_than_or_equal_to_limit_1438;

import java.util.TreeMap;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/21 14:57
 * @since 1.0.0
 */
public class Solution1 {
    public int longestSubarray(int[] nums, int limit) {
        // 无法采用堆，因为并非right直接与堆顶比较，而是列表中的最大/最小值做比较因此采用红黑树更合适，有序树结构便于找到最大、最小
        TreeMap<Integer,Integer> map = new TreeMap<>();
        // 滑动窗口
        int left = 0, right = 0;
        int res = 0;
        int n = nums.length;
        while (right<n){
            map.put(nums[right],map.getOrDefault(nums[right],0)+1);
            while (map.lastKey() - map.firstKey() > limit){
                // 通过移除left来确保最左侧
                // 超出限制减少left
                map.put(nums[left],map.get(nums[left])-1);
                if (map.get(nums[left]) == 0){
                    // 清空
                    map.remove(nums[left]);
                }
                left++;
            }
            res = Math.max(res,right-left+1);
            right++;
        }
        return res;
    }
}
