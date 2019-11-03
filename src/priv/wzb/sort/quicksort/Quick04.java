package priv.wzb.sort.quicksort;

import priv.wzb.sort.Util;

import java.io.Reader;
import java.io.Writer;

/**
 * @author Satsuki
 * @time 2019/10/24 13:22
 * @description:
 */
public class Quick04 {
    public static void sort(int[] arr){
        qsort(arr,0,arr.length-1);
    }

    public static void qsort(int[] arr,int low,int high){
        if (low<high){
            // 找到枢纽
            int pivot = partition(arr,low,high);
//            Util.printf(arr);
            // 递归排序左边数组
            qsort(arr,low,pivot-1);
            // 递归排序右边数组
            qsort(arr,pivot+1,high);

//            Throwable
//            Writer
        }

//        Util.printf(arr);
//        Thread


    }

    // 寻找数组中心枢纽位置
    // 分割数组
    public static int partition(int[] arr,int low,int high){
        // 初始化为最左边下标
        int pivot = arr[low];


        // 循环分类
        while (low<high){
            // 从最右边开始找直到找到一个比枢纽小的
            while (low<high && arr[high]>=pivot) high--;
            // 将该数值放到枢纽左边
            arr[low] = arr[high];
            // 从最左边开始寻找找到一个比枢纽大的放到枢纽右边
            while (low<high && arr[low]<pivot) low++;
            // 将找到数值放到枢纽右边
            arr[high] = arr[low];

        }

        // 一次分割完成
        // 枢纽放到中间
        // 中间下标为low(此时low== high)
        arr[low] = pivot;


        return low;
    }

    public static void main(String[] args) {
//        int[] a = {0,10,9,2,60,10,30,40,90,50,4,6,4,3,1};
//        sort(a);

        long now = System.currentTimeMillis();
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int)(Math.random() * 80000000);// 生成一个[0,8000000]的随机数
        }
        sort(arr);
        long last = System.currentTimeMillis();
        System.out.println("花费时间：" + (last-now));
        System.out.println(arr.length);
    }
}
