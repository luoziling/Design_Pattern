package priv.wzb.sort.mergesort;

import priv.wzb.sort.Util;

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
        // 优化：因为arr[L,mid]和arr[mid+1,R]已经有序，
        // 所以如果满足这个条件，就不要排序了,防止一开始数组有序
        if(arr[mid]>arr[mid+1])
            merge(arr,L,mid,R);

        Util.printf(arr);

    }

    static void merge(int[] arr,int L,int mid,int R){
        // 申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列
        int[] help = new int[R-L+1];
        // 申请空间的下标
        int k = 0;
        // 设定两个指针，最初位置分别为两个已经排序序列的起始位置
        int p1=L,p2=mid+1;
        while (p1<=mid&&p2<=R)
            // 比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置
            help[k++] = arr[p1]<=arr[p2]?arr[p1++]:arr[p2++];//左右两边相等的话，就先拷贝左边的(实现稳定性)
        // 将另一序列剩下的所有元素直接复制到合并序列尾
        while (p1<=mid)//左边剩余部分
            help[k++] = arr[p1++];
        while (p2<=R)//右边剩余部分
            help[k++] = arr[p2++];
        for(int i=0;i<k;i++)//拷贝回原来的数组
            arr[i+L] = help[i];

        Util.printf(arr);
    }

    public static void sort1(int arr[]){
        mergeProcess(arr,0,arr.length-1);
    }
    public static void main(String[] args) {
        int[] a = {0,10,9,7,5,6,4,2,1};

        int arr[] = {8,4,5,7,1,3,6,2};
//        sort(a);
        sort1(a);
        sort1(arr);
    }
}
