package priv.wzb.design_pattern.quicksort;

/**
 * @author Satsuki
 * @time 2019/5/6 16:39
 * @description:
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
