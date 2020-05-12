package priv.wzb.leet_code.array.sort_an_array_912;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Satsuki
 * @time 2020/3/31 17:20
 * @description:
 * 给你一个整数数组 nums，请你将该数组升序排列。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [5,2,3,1]
 * 输出：[1,2,3,5]
 * 示例 2：
 *
 * 输入：nums = [5,1,1,2,0,0]
 * 输出：[0,0,1,1,2,5]
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 50000
 * -50000 <= nums[i] <= 50000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sort-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 基本快速排序
 */
public class Solution2 {
    // 阈值 数列大于这个优先使用快速否则使用插入
    private static final int INSERTION_SORT_THRESHOLD=7;

    private static final Random RANDOM = new Random();

    public int[] sortArray(int[] nums) {
        int len = nums.length;
        quickSort(nums,0,len-1);
        return nums;
    }

    private void quickSort(int[] nums,int left,int right){
        // 小区间使用插入排序
        if (right - left <= INSERTION_SORT_THRESHOLD){
            insertionSort(nums,left,right);
            System.out.println(2);
            return;
        }
        int pIndex = partition(nums,left,right);
        quickSort(nums,left,pIndex-1);
        quickSort(nums,pIndex+1,right);
    }

    private void insertionSort(int[] nums,int left,int right){
        for (int i = 1; i <= right; i++) {
            int temp = nums[i];
            int j=i;
            // 插入排序的核心
            // 在当前位置不断的向前查找如果前一个数字大于当前待排序数字就将前一个数字后移使大数在后面的位置
            // 直到找到当前数字合适的插入位置位置
            while (j > left && nums[j-1] > temp){
                nums[j] = nums[j-1];
                j--;
            }
            nums[j] = temp;
        }
    }

    private int partition(int[] nums,int left,int right){
        System.out.println(111);
        // 随机找一个枢纽数值
        int randomIndex = RANDOM.nextInt(right-left+1)+left;
        // 把这个数值放到最左侧
        swap(nums,left,randomIndex);

        // 枢纽值
        int pivot = nums[left];
        // 待替换值的下标
        int lt = left;

        for (int i = left+1; i <= right; i++) {
            if (nums[i]<pivot){
                // 找到比枢纽值小的就替换两者位置
                // 21347568
                // 28413567
                // 41327568
                // 13247568
                // 43217568
                lt++;
                swap(nums,i,lt);
            }
        }
        swap(nums,left,lt);
        System.out.println(1);
        return lt;

    }

    private int partition1(int[] nums,int l,int r){
        int pivot = nums[r];
        int i = l-1;
        for (int j = 0; j <= r - 1; j++) {
            if (nums[j]<=pivot){
                i=i+1;
                swap(nums,i,j);
            }
        }
        swap(nums,i+1,r);
        return i+1;
    }

    private void swap(int[] nums,int index1,int index2){
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }


    public static void main(String[] args) {
        System.out.println(new Solution2().sortArray(new int[]{2,1,3,4,7,5,6,8,9}));
    }


}
