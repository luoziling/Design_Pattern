package priv.wzb.sort.select;

import priv.wzb.sort.Util;

/**
 * @author Satsuki
 * @time 2019/10/30 17:47
 * @description:
 * 算法 先简单--》做复杂
 * 把复杂算法拆分为简单问题
 * 再逐步解决
 */
public class SelectSort {
    public static void main(String[] args) {
//        int[] arr = new int[]{101,34,119,1,-1,90,113};
//        selectSort(arr);
//        Util.printf(arr);

//        int[] a = {0,10,9,2,4,6,4,3,1};
//        selectSort(a);
//        Util.printf(a);

        // 测试以下冒泡排序的速度O(n²) 给80000个数据测试
        int[] arr = Util.getTestArr();

        long nowTime = System.currentTimeMillis();
        selectSort(arr);
        long afterTime = System.currentTimeMillis();

        System.out.println("共花费时间(ms):" + (afterTime-nowTime));
    }

    public static void selectSort(int[] arr){

        int length = arr.length;
        int temp;
        int extremeIndex;
        // 注意length-1 一共n个数如果n-1个都有序那么最后一个也有序
        for (int i = 0; i < length-1; i++) {

            extremeIndex = i;
            // 假设前i个有序
            for (int j = i+1; j < length; j++) {
                // 从小到大
                // 注意，进行对比的是从当前值的下一个值开始于被认为的最小值比较找出最小值（极值）
                if (arr[j]<arr[extremeIndex]){
                    extremeIndex = j;
                }
            }
            // 优化
            if (extremeIndex != i){
                // 如果极值下标改变过再交换
                temp = arr[i];
                arr[i] = arr[extremeIndex];
                arr[extremeIndex] = temp;
            }
//            temp = arr[i];
//            arr[i] = arr[extremeIndex];
//            arr[extremeIndex] = temp;
        }
    }
}
