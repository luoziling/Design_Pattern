package priv.wzb.sort.mergesort;

import priv.wzb.sort.Util;

/**
 * @author Satsuki
 * @time 2019/10/31 20:51
 * @description:
 * 分治算法
 */
public class MergeSort02 {
    public static void main(String[] args) {
//        int arr[] = {8,4,5,7,1,3,6,2};
//        sort1(arr);
//        Util.printf(arr);

        long now = System.currentTimeMillis();
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 8000000);// 生成一个[0,8000000]的随机数
        }
        sort1(arr);
        long last = System.currentTimeMillis();
        System.out.println("花费时间：" + (last-now));
        System.out.println(arr.length);
    }


    public static void sort1(int arr[]){
//        mergeProcess(arr,0,arr.length-1);
        m(arr,0,arr.length-1);
    }

    /**
     * 分离
     * @param arr
     * @param left
     * @param right
     */
    public static void m(int arr[],int left,int right){
        if (left>=right){
            return;
        }
        int mid = left+((right-left)>>1);
        m(arr,left,mid);
        m(arr,mid+1,right);

//        merge(arr,left,mid,right);

        if(arr[mid]>arr[mid+1])
            merge(arr,left,mid,right);
    }


    /**
     * 合并
     * @param arr
     * @param left
     * @param mid
     * @param right
     */
    public static void merge(int[] arr,int left,int mid,int right){
        int[] help = new int[right-left+1];
        int i = left; //左边有序序列的初始索引
        int j = mid+1; //右边有序序列的初始索引
        int k = 0;

        // 归并组合
        while (i<=mid&&j<=right){
            help[k++] = arr[i]<=arr[j]?arr[i++]:arr[j++];
        }

        //左边未组合的入队
        while (i<=mid){
            help[k++] = arr[i++];
        }

        // 右边未组合的入队
        while (j<=right){
            help[k++] = arr[j++];
        }

        // 归队
        for (int l = 0; l < k; l++) {
            // 一定要加上left因为left相当于偏移量，并不一定每次合并的数组都是从原数组的第零个开始的
//            arr[l] = help[l];
            arr[l+left] = help[l];
        }

//        System.arraycopy(help,0,arr,0,k);
    }
}
