package priv.wzb.leet_code.stack_queue_heap.kth_largest_element_in_an_array_215;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author Satsuki
 * @time 2019/11/10 18:19
 * @description:
 * 使用小顶堆
 * 若堆中元素不满k个就入堆
 * 满了k个之后继续遍历元素如果元素大于堆顶就入堆
 * 那么最后堆顶就是要找的元素
 */
public class Solution1 {
    public int findKthLargest(int[] nums, int k) {
        // init heap the smallest element first
        PriorityQueue<Integer> heap = new PriorityQueue<>((n1,n2)->n1-n2);

        // keep k largest elements in the heap
        for(int n:nums){
            heap.add(n);
            if (heap.size()>k){
                heap.poll();
            }
        }

        return heap.poll();
    }
}
