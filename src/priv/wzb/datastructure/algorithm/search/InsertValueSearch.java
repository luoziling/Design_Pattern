package priv.wzb.datastructure.algorithm.search;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/1 15:06
 * @description:
 * 插值查找
 * 在查找的数组分散的比较大（分布不均匀）时可能折半查找更好
 */
public class InsertValueSearch {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i+1;
        }
        System.out.println(Arrays.toString(arr));
        int index = insertValueSearch(arr, 0, arr.length - 1, 43);
        System.out.println("index =" + index);
    }

    // 插值查找
    // 插值查找也要求数组有序
    public static int insertValueSearch(int[] arr,int left,int right,int findVal){
        // 递归退出条件之一
        // 不仅优化而且防止越界
        System.out.println("hello");
        if (left>right || findVal<arr[0] || findVal>arr[arr.length-1]){
            // 当找完/小于最小值/大于最大值就退出
            System.out.println("left>right" + (left>right));
            System.out.println("findVal<arr[0]:" + (findVal<arr[0]));
            System.out.println("findVal>arr[arr.length-1]:" + (findVal>arr[arr.length-1]));
            return -1;
        }

        // 求mid 自适应（要查找的值加入运算）
        // 从二分查找到插值查找就是优化了mid的算法
        // 类似于根据要查找值在数组中的大概位置跨越一个步长 进行定位
        // 从最左侧开始加上一个步长（数组总长度 * 要查找数字在数组大概为止的比例）
        int mid = left + (right-left) * (findVal-arr[left])/(arr[right] - arr[left]);
        int midVal = arr[mid];
        if (findVal>midVal){
            // 要找的值大于midValue
            // 向右递归查找
            return insertValueSearch(arr,mid+1,right,findVal);
        }else if (findVal<midVal){
            // 要找的值大于midValue
            // 向右递归查找
            return insertValueSearch(arr,left,mid-1,findVal);
        }else {
            return mid;
        }
    }
}
