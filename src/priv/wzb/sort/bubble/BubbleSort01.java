package priv.wzb.sort.bubble;

import priv.wzb.sort.Util;

/**
 * @author Satsuki
 * @time 2019/9/14 14:56
 * @description:
 */
public class BubbleSort01 {
    public static void sort(int[] arr){
        Util.printf(arr);
        int temp;
        //要比较n-1次
        for (int i = 0; i < arr.length - 1; i++) {
            //从0到n-i-1 默认最后的一些数是有序的
            for (int j = 0; j < arr.length-i-1; j++) {
                if (arr[j]>arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        Util.printf(arr);
    }

    public static void main(String[] args) {
        int[] a = {0,10,9,2,4,6,4,3,1};
        sort(a);
    }


}
