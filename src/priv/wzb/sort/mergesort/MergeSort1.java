package priv.wzb.sort.mergesort;

import priv.wzb.sort.Util;

/**
 * @author Satsuki
 * @time 2019/10/24 14:09
 * @description:
 */
public class MergeSort1 {

    public static void merge(int[] arr,int low,int mid,int high){
        // 创建辅助数组
        int[] help = new int[high-low+1];
        // 辅助数组下标
        int k=0;
        // 设定两个指针指向两个数组初始位置
        int p1 = low,p2=mid+1;

        // 合并
        while (p1<=mid&&p2<=high){
            help[k++] = arr[p1]<=arr[p2]?arr[p1++]:arr[p2++];
        }

        // 把剩余部分放入归并数组
        while (p1<=mid){
            help[k++] = arr[p1++];
        }

        while (p2<=high){
            help[k++] = arr[p2++];
        }

        // 拷贝回原数组
        for (int i = 0; i < k; i++) {
            // 这边是i+low
            // 原因:虽然分块了，但这只是逻辑上的，真实情况还是在原来数组上操作
            // 要加偏移量low不然回从头开始覆盖陷入错误
            arr[i+low] = help[i];
        }



        Util.printf(arr);
    }

    public static void mergeSort(int[] arr,int low, int high){

        // 递归跳出
        if (low>=high){
            return; // 递归返回
        }


        // 使用递归拆分数组
        // 将数组拆分为n个子数组直至数组大小为2
        // 确定中值
        // 注意后面的(high-low)>>1一定要加小括号
        // 因为<< >>左移右移运算符（*2 /2）优先级小于+
        int mid = low + ((high-low)>>1);
        // 递归拆分
        mergeSort(arr,low,mid);
        mergeSort(arr,mid+1,high);

        // 归并
        // 防止有序优化算法
        if (arr[mid]>arr[mid+1]){
            merge(arr,low,mid,high);
        }

        Util.printf(arr);
    }


    public static void sort(int[] arr){
        mergeSort(arr,0,arr.length-1);
    }
    public static void main(String[] args) {
        int[] a = {0,10,9,7,5,6,4,2,1};
//        sort(a);
        sort(a);
    }
}
