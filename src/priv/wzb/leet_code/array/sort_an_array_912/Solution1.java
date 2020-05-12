package priv.wzb.leet_code.array.sort_an_array_912;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 */
public class Solution1 {
    public int[] sortArray(int[] nums) {
        // 快排
        qsort(nums,0,nums.length-1);
        return nums;
    }

    /**
     *
     * @param arr 待排序数组
     * @param low 数组头节点
     * @param high 数组尾节点
     */
    public static void qsort(int[] arr,int low,int high){
        // 递归排序
        // 设置递归退出点
        if (low<high){
            // 找出枢纽位置
            int pivot = partition(arr,low,high);
            // 递归左排序
            qsort(arr,0,pivot-1);
            // 递归右排序
            qsort(arr,pivot+1,high);
        }
    }

    /**
     * 找出枢纽，分割数组
     * @param arr 待排序数组
     * @param low 数组头节点
     * @param high 数组尾节点
     * @return 枢纽
     */
    public static int partition(int[] arr,int low,int high){
        // 假设枢纽为最左侧
        // 这里的pivot代表枢纽在数组中对应的数值
        // low就是枢纽下标（位置）
        // 并且记录枢纽代表的数值
        // 这个数值在循环中会被磨削
        int pivot = arr[low];

        // 根据枢纽为基准把比枢纽小的放在左边比枢纽大的放在右边
        while (low<high){
            // 从右边开始寻找
            // 找到比枢纽小的放到枢纽左边
            while (low <high&&arr[high]>=pivot){
                high--;
            }

            arr[low] = arr[high];

            // 从左边开始找找到比枢纽大的放到枢纽右边
            while (low<high&& arr[low]<pivot){
                low++;
            }
            arr[high] = arr[low];
        }

        // 循环停止low=high
        // 恢复pivot代表的数值
        arr[low] = pivot;

        return low;



    }



    public static void main(String[] args) {
        System.out.println(new Solution1().sortArray(new int[]{5,2,3,1}));
    }


}
