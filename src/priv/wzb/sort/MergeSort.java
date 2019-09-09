package priv.wzb.sort;

/**
 * @author Satsuki
 * @time 2019/5/12 17:17
 * @description:
 */
public class MergeSort {
    static void mergeSort(int[] arr){
        float a = 1.1f;
        if(arr == null || arr.length<=1){
            return;
        }

    }

    static void mergeProcess(int[] arr,int L,int R){
        if(L>=R)
            return;//递归条件判断
        int mid = L + ((R-L)>>1);//这个相当于(R+L)/2;
        mergeProcess(arr,L,mid);//T(n/2)注意不是mid-1
        mergeProcess(arr,mid+1,R);//T(n/2)
        //优化：因为arr[L,mid]和arr[mid+1,R]已经有序，
        // 所以如果满足这个条件，就不要排序了,防止一开始数组有序
        if(arr[mid]>arr[mid+1])
            merge(arr,L,mid,R);

    }

    static void merge(int[] arr,int L,int mid,int R){
        int[] help = new int[R-L+1];
        int k = 0;
        int p1=L,p2=mid+1;
        while (p1<=mid&&p2<=R)
            help[k++] = arr[p1]<=arr[p2]?arr[p1++]:arr[p2++];//左右两边相等的话，就先拷贝左边的(实现稳定性)
        while (p1<=mid)//左边剩余部分
            help[k++] = arr[p1++];
        while (p2<=R)//右边剩余部分
            help[k++] = arr[p2++];
        for(int i=0;i<k;i++)//拷贝回原来的数组
            arr[i+L] = help[i];
    }

    public static void sort1(int arr[]){

    }
    public static void main(String[] args) {
        int[] a = {0,10,9,7,5,6,4,2,1};
//        sort(a);
        sort1(a);
    }
}
