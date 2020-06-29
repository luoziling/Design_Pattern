package priv.wzb.leet_code.stack_queue_heap.kth_largest_element_in_an_array_215;

import java.util.PriorityQueue;

/**
 * @author Satsuki
 * @time 2019/11/10 18:19
 * @description:
 * 堆解法
 */
public class Solution2 {
    // 构建小顶堆
    private PriorityQueue<Integer> maxHeap;
    public int findKthLargest(int[] nums, int k) {
        maxHeap = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            // 放排序号的前N个进堆
            if (maxHeap.size()<k){
                maxHeap.offer(nums[i]);
                // 找到更大的就去取出小丁堆中最小的并加入更大的元素
                // 这样遍历完成后小顶堆存放前N个排序号的元素且堆顶是第N大的元素
            }else if (nums[i]>maxHeap.peek()){
                maxHeap.poll();
                maxHeap.offer(nums[i]);
            }
        }
        return maxHeap.peek();
    }


    public static void main(String[] args) {
        int[] nums = {3,2,1,5,6,4};
        int k  = 2;
        System.out.println(new Solution2().findKthLargest(nums,k));
    }
}
