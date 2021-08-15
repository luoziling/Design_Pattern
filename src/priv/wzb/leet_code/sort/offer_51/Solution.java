package priv.wzb.leet_code.sort.offer_51;

import java.util.Arrays;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/8/15 23:17
 * @description:
 * @since 1.0.0
 */
public class Solution {
    static int count = 0;
    public static int reversePairs(int[] nums) {
//        int count = 0;
//        // 暴力遍历
//        for (int i = 0; i < nums.length-1; i++) {
//            if (nums[i+1]<nums[i]){
//                count++;
//            }
//        }
//        return count;
        // 思路 归并排序，归并与逆序对，合并的是否nums1[i]>nums2[j] 则意味着 从i开始到结束与j组合都是逆序
        // 逆序 排序，归并排序解决问题
        int[] copy = Arrays.copyOf(nums, nums.length);
        divideSort(copy, 0, nums.length - 1);
        return count;
    }

    /**
     * 分割
     *
     * @param nums
     * @param start
     * @param end
     */
    private static void divideSort(int[] nums, int start, int end) {
        // 递归中止

        if (start < end) {
            // 分
            int mid = (start + end) / 2;
            divideSort(nums, start, mid);
            divideSort(nums, mid + 1, end);
            // 合
            merge(nums, start, mid, end);
        }

    }

    /**
     * 合
     * 不管如何二分， 始终是一个数组的有序二分
     * @param nums
     * @param start
     * @param mid
     * @param end
     */
    private static void merge(int[] nums, int start, int mid, int end){
        // 申请多大的空间呢 由于是有序的二分所以start/end就是整个二分前数组的大小
        int[] help = new int[end-start+1];
        // 左右数组起点
        int l0 = start;
        int l1 = mid+1;
        int k = 0;
        // 遍历组合，同时统计逆序
        while (l0<=mid && l1<=end){
            // 若正好有序就顺序放入
            if (nums[l0]<=nums[l1]){
                help[k++] = nums[l0++];
            }else {
                // 无序统计逆序，由于归并过程中l0/l1两个数组都是有序的，所以l0>l1当前位置那么l0后面的都大于l1当前位置
                count+=(mid-l0+1);
                help[k++] = nums[l1++];
            }
        }
        // 剩下有未合并的继续合并
        while (l0<=mid){
            help[k++] = nums[l0++];
        }
        while (l1<=end){
            help[k++] = nums[l1++];
        }
        //拷贝回原来的数组
        for(int i=0;i<k;i++) {
            nums[i + start] = help[i];
        }
    }
    public static void main(String[] args) {
//        int[] a = {0,10,9,7,5,6,4,2,1};
//        int[] a = {7,5,6,4};
//        int[] a = {7,3,2,6,0,1,5,4};
        int[] a = {4,5,6,7};

        int arr[] = {8,4,5,7,1,3,6,2};
//        sort(a);
//        divideSort(a,0,a.length-1);
        reversePairs(a);
        System.out.println("count = " + count);
//        sort1(arr);
    }
}
