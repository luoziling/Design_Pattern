package priv.wzb.sort.quicksort;

/**
 * @author Satsuki
 * @time 2019/5/6 16:23
 * @description:
 */
public class QuickSort {
    public static void quickSort(int[] arr){
        qsort(arr,0,arr.length-1);
    }
    private static void qsort(int[] arr,int low,int high){
        if(low<high){
            int pivot = partition(arr,low,high);//将数组分为两部分
            qsort(arr,low,pivot-1);//递归排序左数组
            qsort(arr,pivot+1,high);//递归排序右数组
        }
        for (int k = 0; k < arr.length; k++) {
            System.out.println(arr[k]);
        }
    }
    private static int partition(int[] arr,int low,int high){
        int pivot = arr[low];//把最左边作为枢轴记录
        while(low<high){
            while (low<high && arr[high]>=pivot) --high;
            arr[low] = arr[high];//交换比枢轴小的记录到左端
            while (low<high && arr[low]<pivot) ++ low;
            arr[high] = arr[low];//交换比枢轴小的记录到右端
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        //返回枢轴位置
        return low;
    }

    public static void sort1(int arr[],int low,int high){
        if(low<high){
            int pivot = partition1(arr,low,high);
//          递归左边
            sort1(arr,low,pivot-1);
            //递归右边
            sort1(arr,pivot+1,high);
        }
        for (int k = 0; k < arr.length; k++) {
            System.out.println(arr[k]);
        }
    }

    //一次循环排序
    private static int partition1(int[] arr,int low,int high){
        //记录枢纽
        int pivot = arr[low];
        while (low<high ){
//          从后向前比较直到找到比枢纽小的就放到枢纽前面
            while (low<high && arr[high]>=pivot)
                high--;
            arr[low] = arr[high];
//          从前往后找直到找到比枢纽大的就放在枢纽后面
            while (low<high && arr[low]<=pivot)
                low++;
            arr[high] = arr[low];
        }
        //枢纽重置位置
        arr[low] = pivot;
        return low;
    }
    public static void main(String[] args) {
        int[] a = {0,10,9,7,5,6,4,2,1};
//        qsort(a,0,8);
//        sort1(a,0,8);
        quickSort(a);
    }
}
