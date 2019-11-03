package priv.wzb.sort.bubble;

import priv.wzb.sort.Util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Satsuki
 * @time 2019/10/30 17:16
 * @description:
 */
public class BubbleSort {
    public static void main(String[] args) {
//        int arr[] = {3,9,-1,10,-2};
//        int arr[] = {3,9,-1,10,20};
//        int arr[] = {1,2,3,4,5,6};
//        System.out.println("排序前");
//        System.out.println(Arrays.toString(arr));
//        bubbleSort(arr);
//        bubbleSort(arr);

        // 测试以下冒泡排序的速度O(n²) 给80000个数据测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 800000);// 生成一个[0,800000]的随机数
        }

//        bubbleSort(arr);
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = simpleDateFormat.format(date);
        long nowTime = System.currentTimeMillis();
        bubbleSort(arr);
        long afterTime = System.currentTimeMillis();

        System.out.println("共花费时间(ms):" + (afterTime-nowTime));

//        System.out.println("排序后");
//        System.out.println();
//        System.out.println(Arrays.toString(arr));

    }

    public static void bubbleSort(int[] arr){
        // 优化
        // 标识符
        boolean flag = false;
        // 演示排序演变过程
        int n = arr.length;
        int temp = 0;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                // 如果前面的数比后面的数大，则交换
                if (arr[j]>arr[j+1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
//            Util.printf(arr);
            // 优化
            if (flag == false){
                // 在一次排序中一次交换都未发生
                break;
            }else {
                // 重置flag进行下次判断
                flag = false;
            }
        }

    }


//    public static void bubbleSort(int[] arr){
//        int n = arr.length;
//        int temp;
//        // 比较n-1次
//        for (int i = 0; i < arr.length-1; i++) {
//            for (int j = 0; j < n - i - 1; j++) {
//                if (arr[j]>arr[j+1]){
//                    temp = arr[j];
//                    arr[j] = arr[j+1];
//                    arr[j+1] = temp;
//                }
//            }
//        }
//    }
}
