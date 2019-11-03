package priv.wzb.sort.quicksort;

import priv.wzb.sort.Util;

/**
 * @author Satsuki
 * @time 2019/9/14 15:08
 * @description:
 * 挑选基准值：从数列中挑出一个元素，称为“基准”（pivot），
 * 分割：重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（与基准值相等的数可以到任何一边）。在这个分割结束之后，对基准值的排序就已经完成，
 * 递归排序子序列：递归地将小于基准值元素的子序列和大于基准值元素的子序列排序。
 */
public class QuickSort02 {
    public static void sort(int[] arr){
        qsort(arr,0,arr.length-1);
    }

    /**
     *  递归排序子序列
     * @param arr
     * @param low
     * @param high
     */
    public static void qsort(int[] arr,int low,int high){
//        Util.printf(arr);

        if (low<high){
            //将数组分为两部分
            int pivot = partition(arr,low,high);
            //递归排序左数组
            qsort(arr,low,pivot-1);
            //递归排序右数组
            qsort(arr,pivot+1,high);
        }
        Util.printf(arr);
    }

    /**
     * 其实这个方法并不是平均将数组分为两部分
     * 但是确实将数组分为了不一定平均的两部分一部分比枢纽小
     * 一部分比枢纽大
     * 在这个过程中其实就是一种排序
     * 将枢纽的位置排到正确的位置上
     *
     * 挑选基准值
     * @param arr
     * @param low
     * @param high
     * @return
     */
    public static int partition(int[] arr,int low,int high){
        //将最左边作为枢纽（pivot）记录
        int pivot = arr[low];
        //分割
        //循环跳出条件 当左边下标大于右边下标就跳出循环
        while (low<high){
            //从数组最右边开始寻找，当最右边的值大于枢纽值则自减
            //直到找到一个值比枢纽小为止
            while (low<high && arr[high]>=pivot)
                --high;
            //找到了比枢纽小的值
            //交换将比枢纽小的值放到左边
            arr[low] = arr[high];
            //从数组最左边开始寻找，当最左边的值小于枢纽值则自加
            //直到找到一个值比枢纽大
            while (low<high && arr[low]<pivot)
                low++;
            //找到了比枢纽大的值 交换
            arr[high] = arr[low];

        }
        //扫描完成
        //枢纽改变位置
        //应该此时low=high下标指向同一个位置才对
        arr[low] = pivot;
        System.out.println("low:" + low+"--high:" + high);

        //error: pivot是记录枢纽的值并不是枢纽位置
        //此处应该返回枢纽位置
//        return pivot;
        Util.printf(arr);
        return low;
    }
    public static void main(String[] args) {
        int[] a = {0,10,9,2,4,6,4,3,1};
        sort(a);
    }
}
