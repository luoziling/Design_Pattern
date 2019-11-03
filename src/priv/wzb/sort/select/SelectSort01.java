package priv.wzb.sort.select;

import static priv.wzb.sort.Util.printf;

/**
 * @author Satsuki
 * @time 2019/9/14 14:31
 * @description:
 */
public class SelectSort01 {
    public static void sort(int[] arr){
        printf(arr);
        int i,j;
        int minIndex;
        int temp;
        //要循环n次
        for (i = 0; i < arr.length; i++) {
           //记录当前下标最为最小数下标
            minIndex = i;
            //默认0-i排好序
            for(j = i+1;j<arr.length;j++)
            {
                //找出最小数的下标
                if (arr[j]<arr[minIndex]){
                    //minIndex永远指向最小数的下标
                    minIndex = j;
                }
            }
            //找到最小数后放到指定位置
            //需要交换
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;

        }
        System.out.println();
        printf(arr);
    }



    public static void main(String[] args) {
        int[] a = {0,10,9,2,4,6,4,3,1};
        sort(a);
    }
}
