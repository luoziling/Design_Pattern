package priv.wzb.sort;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/9/14 14:55
 * @description:
 */
public class Util {
    public static void printf(int[] arr){
        System.out.println(Arrays.toString(arr));
    }

    public static int[] getTestArr(){
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 8000000);// 生成一个[0,8000000]的随机数
        }
        return arr;
    }
}
