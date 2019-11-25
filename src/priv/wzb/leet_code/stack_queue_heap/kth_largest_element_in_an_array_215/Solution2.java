package priv.wzb.leet_code.stack_queue_heap.kth_largest_element_in_an_array_215;

import java.util.PriorityQueue;

/**
 * @author Satsuki
 * @time 2019/11/10 18:19
 * @description:
 * 堆解法
 */
public class Solution2 {
    // 构建大顶堆
    private PriorityQueue<Integer> maxHeap;
    public int findKthLargest(int[] nums, int k) {
        maxHeap = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            if (maxHeap.size()<k){
                maxHeap.offer(nums[i]);
            }else if (nums[i]>maxHeap.peek()){
                maxHeap.poll();
                maxHeap.offer(nums[i]);
            }
        }
        return maxHeap.peek();
    }
}
