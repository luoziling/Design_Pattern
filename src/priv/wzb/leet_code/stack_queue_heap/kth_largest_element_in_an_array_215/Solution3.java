package priv.wzb.leet_code.stack_queue_heap.kth_largest_element_in_an_array_215;

/**
 * @author Satsuki
 * @time 2020/6/29 13:36
 * @description:
 */
public class Solution3 {
    public int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        buildMaxHeap(nums,heapSize);
        for (int i = nums.length-1; i >= nums.length - k + 1; --i) {
            swap(nums,0,i);
            --heapSize;
            maxHeapify(nums,0,heapSize);
        }
        return nums[0];
    }

    public void buildMaxHeap(int[] a,int heapSize){
        for (int i = heapSize/2; i >= 0; i--) {
            maxHeapify(a,i,heapSize);
        }
    }

    public void maxHeapify(int[] a,int i,int heapSize){
        int l = i*2+1,r=i*2+2,largest = i;
        if (l<heapSize&&a[l]>a[largest]){
            largest = l;
        }
        if (r < heapSize && a[r] > a[largest]) {
            largest = r;
        }
        if (largest!=i){
            swap(a,i,largest);
            maxHeapify(a,largest,heapSize);
        }
    }

    public void swap(int[] a, int i,int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
