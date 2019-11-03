package priv.wzb.sort.quicksort;

import priv.wzb.sort.Util;

/**
 * @author Satsuki
 * @time 2019/9/14 15:50
 * @description:
 * 1.找出枢纽
 * 2.分割成两部分
 * 3.递归分别排左边和右边
 */
public class QuickSort03 {

    public static void sort(int[] arr){
        qsort(arr,0,arr.length-1);

    }

    //递归排序
    public static void qsort(int[] arr,int low,int high){
        if (low<high){
            //找枢纽的位置
            int pivot = partition(arr,low,high);
            //递归排序左侧数组
            qsort(arr,low,pivot-1);
            //递归排序右侧数组
            qsort(arr,pivot+1,high);
        }

        Util.printf(arr);

    }

    //找出枢纽
    //分割数组
    public static int partition(int[] arr,int low,int high){
        //假设枢纽为数组开头第一个数字
        int pivot = arr[low];

        //循环条件当左边下标小于右边下标时循环分割数组
        //当条件不满足就是左边下标等于右边下标
        //也就是找到了pivot枢纽应该存放的位置
        //将枢纽归位即可
        while (low < high){
            //从最右边开始找
            //如果右侧数值大于pivot枢纽的数值则继续向前找即可
            while (low < high && arr[high]>=pivot){
                high--;
            }
            //当从右边开始找找到了一个小于枢纽并且存在于枢纽右边的数字时
            //将该数值扔到枢纽左边
            arr[low] = arr[high];

            //从右边找完就应该从左边再开始找
            //如果左边数值小于pivot则继续向后找
            while (low<high && arr[low]<pivot){
                low++;
            }
            //找到一个数值大于枢纽并且在枢纽左边
            //扔到枢纽右边
            arr[high] = arr[low];
        }

        //全部循环玩一遍之后当low=high时
        //就找到了枢纽应该存在的位置
        //就是给枢纽排序了
        //枢纽存在位置左边的数字永远小于枢纽
        //pivot存在位置右边的数字永远大于pivot
        //枢纽归位
        //此时的low==high
        arr[low] = pivot;

        //返回枢纽位置
        return low;

    }

    public static void main(String[] args) {
        int[] a = {0,10,9,2,4,6,4,3,1};
        sort(a);
    }
}
