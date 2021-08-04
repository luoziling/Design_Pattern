package priv.wzb.sort.quicksort;

/**
 * @author Satsuki
 * @time 2019/5/6 16:39
 * @description:
 * 快速排序是特殊的归并排序
 * 归并与快排的区别在于归并强调middle的中间值，然后根据中间值不断切割，先分割
 * 后汇总，汇总过程根据middle将数组分为两部分，两部分遍历有序合并到同一数组中
 * 基数排序则是空间换时间，去最大数字的位数开辟相应数量的桶，将数字有序放入桶中，最后合并
 */
public class QuickSort1 {
    public static void quickSort(int[] arr){
        qsort(arr,0,arr.length-1);
    }
    private static void qsort(int[] arr,int low,int high){
        if(low<high){
            int pivot = partition(arr,low,high);
            qsort(arr,low,pivot-1);
            qsort(arr,pivot+1,high);
        }
    }

    private static int partition(int[] arr,int low,int high){
        //从左边开始设置枢轴
        int pivot = arr[low];

        while (low<high){
            //当左边下标小于右边下标且从最右边下标开始的值大于枢轴则往前找
            while (low<high && arr[high]>pivot)
                high--;
            //找到左边下标不小于后边下标则结束循环
            //或者找到第一个右边下标的值小于枢轴
            //调换顺序把比枢轴小的放到枢轴左边
            arr[low] = arr[high];

            //当左边下标小于右边下标且从最左边下标开始的值小于枢轴则往后找
            while (low<high && arr[low]<pivot)
                low++;
            //调换顺序把比枢轴大的放到枢轴右边
            arr[high] = arr[low];

        }
        //扫描完成，枢轴到位
        arr[low] = pivot;

        return low;
    }
}
