package priv.wzb.leet_code.slidingwindow.longest_continuous_subarray_with_absolute_diff_less_than_or_equal_to_limit_1438;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/21 14:57
 * @since 1.0.0
 */
public class Solution {
    public int longestSubarray(int[] nums, int limit) {
        // 需要双队列来维护，单队列只有一半
        // 滑动窗口
        int left = 0, right = 0;
        int res = 0, sum = 0;
        int n = nums.length;
        Queue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o));
        while (right < n) {
            int nowNum = nums[right];
            // 出堆的情况，
            while (Objects.nonNull(priorityQueue.peek()) && Math.abs(priorityQueue.peek()-nowNum) > limit){
                // 只有移动到指定最小值位置才出堆
                if (nums[left] == priorityQueue.peek()){
                    priorityQueue.poll();
                }
                left++;
            }
            // 如果新入的数比堆中最小还要小则移动left
            if (Objects.nonNull(priorityQueue.peek()) && nowNum>priorityQueue.peek()){
                left = right;
                while (!priorityQueue.isEmpty()){
                    priorityQueue.poll();
                }
            }
            // 入堆，一定要在出堆之后
            priorityQueue.offer(nowNum);

            res = Math.max(res,right-left+1);
            right++;
        }
        return res;
    }

    public static void main(String[] args) {
//        Queue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o));
//        priorityQueue.offer(1);
//        priorityQueue.offer(5);
//        priorityQueue.offer(3);
//        System.out.println("priorityQueue.peek() = " + priorityQueue.peek());
        int[] test = {1,5,6,7,8,10,6,5,6};
        System.out.println("new Solution().longestSubarray(test,4) = " + new Solution().longestSubarray(test, 4));
    }
}
