package priv.wzb.sort.select;

/**
 * @author Satsuki
 * @time 2019/6/19 14:13
 * @description:
 */
public class TestSelectSort {
    public static void sort(int arr[]){
        int temp=0;
        //一共比较n次
        for (int i = 0; i < arr.length; i++) {
            //认为目前的数就是最小的，记录最小数的下标
            int minIndex=i;
            //从i+1开始比较到最后一共
            for(int j=i+1;j<arr.length;j++){
                //比较大小总是记录最小数值的大小
                if(arr[minIndex]>arr[j]){
                    minIndex = j;
                }
            }
            //最小数位置不对则交换前i个数字总是有序
            if (i != minIndex) {
                temp = arr[i];
                arr[i]=arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    public static void sort1(int arr[]){
        int temp=0;
        for (int i = 0; i < arr.length; i++) {
            int minIndex=i;
            for (int j = i+1; j < arr.length; j++) {
                if(arr[j]<arr[minIndex]){
                    minIndex=j;
                }
            }
            if(minIndex!=i){
                temp=arr[minIndex];
                arr[minIndex]=arr[i];
                arr[i]=temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {0,10,9,7,5,6,4,2,1};
        sort(a);
//        sort1(a);
    }
}
