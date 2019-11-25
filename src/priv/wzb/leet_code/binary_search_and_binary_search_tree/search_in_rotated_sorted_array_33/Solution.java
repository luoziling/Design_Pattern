package priv.wzb.leet_code.binary_search_and_binary_search_tree.search_in_rotated_sorted_array_33;

/**
 * @author Satsuki
 * @time 2019/11/24 20:12
 * @description:
 */
public class Solution {
    int[] nums;
    int target;

    /**
     *  通过二分查找找到旋转点
     * @param left 左侧下标
     * @param right 右侧下标
     * @return
     * [4,5,6,7,0,1]
     */
    public int findRotateIndex(int left,int right){
        // 如果是有旋转，那么最左侧的肯定大于最右侧的
        // 如果小于说明了没有旋转
        if (nums[left]<nums[right]){
            return 0;
        }

        while (left<=right){
            // 二分查找找旋转点
            int pivot = (left+right)/2;
            // 如果一个点的右侧大于自己说明这个点就是旋转点
            // 因为正常情况下是升序从小到大的数组这样不会出现右侧大于自身的情况
            // 如果出现了就说明进行了旋转且这个点就是旋转点
            if (nums[pivot]>nums[pivot+1])
                return pivot+1;
            else{
                // 正常情况就是最左侧肯定小于这个选定的枢纽pivot
                // 有序数组最左侧一般是最小的
                // 若nums[pivot]<nums[left]就说明了pivot在旋转点之后的数组中旋转点就在pivot左侧
                // 否则就在pivot右侧
                if (nums[pivot]<nums[left])
                    right  = pivot -1;
                else
                    left = pivot+1;

            }
        }
        return 0;
    }

    /**
     * 二分查找
     * @param left
     * @param right
     * @return
     */
    public int search(int left,int right){
        while (left<=right){
            int pivot = (left+right)/2;
            if (nums[pivot] == target){
                return pivot;
            }else if (nums[pivot]>target){
                right = pivot-1;
            }else {
                left = pivot+1;
            }
        }
        return -1;
    }
    public int search(int[] nums, int target) {
        this.nums = nums;
        this.target = target;

        int n = nums.length;

        // 没有元素
        if (n == 0){
            return  -1;
        }
        if (n==1)
            // 只有一个元素就判断这个元素是否是目标元素
            return this.nums[0] == target?0:-1;
        // 通过二分查找去找旋转点
        int rotateIndex = findRotateIndex(0,n-1);

        // 如果目标是最小的元素
        if (nums[rotateIndex] == target){
            return rotateIndex;
        }
        // 如果数组为旋转，搜索整个数组
        if (rotateIndex == 0){
            return search(0,n-1);
        }
        if (target<nums[0]){
            // 如果目标元素小于最左侧元素，在一般情况下就说明目标元素在旋转点的右边
            // 旋转点右侧也是有序的直接在右侧二分查找即可
            return search(rotateIndex,n-1);
        }
        // 在左侧找
        return search(0,rotateIndex);
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,3};
        System.out.println(new Solution().search(a,3));
    }
}
