package priv.wzb.sort.redix;

import priv.wzb.sort.Util;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/10/31 21:53
 * @description:
 */
public class RedixSort {
    public static void main(String[] args) {
//        int arr[] = {53,3,542,748,14,214};
//
//        redixSort(arr);

        // 80000000八千万数据占用内存 80000000 * 11 * 4 /1024 /1024 = 3,356.93359375‬MB
        // 可能出现OutOfMemoryError Java Heap Space
        // Java堆空间不足
        long now = System.currentTimeMillis();
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int)(Math.random() * 80000000);// 生成一个[0,8000000]的随机数
        }
        redixSort(arr);
        long last = System.currentTimeMillis();
        System.out.println("花费时间：" + (last-now));
        System.out.println(arr.length);
//        Util.printf(arr);
    }

    //基数排序
    public static void redixSort(int[] arr){
        // 根据前面的推导可以得到对基数排序的算法
        
        //1.先得到数组中最大的数的位数
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i]>max){
                max = arr[i];
            }
        }

        // 得到最大数的位数
        int maxLength = (max + "").length();


        // 第一轮排序（针对每个元素的个位进行排序处理）

        // 定义一个二维数组，表示十个桶，每个桶就是一个一维数组
        // 二维数组包含十个一维数组
        // 为了防止栈溢出（数据溢出）
        // 把每个一维数组（桶）尽可能定大一些
        int[][] bucket = new int[10][arr.length];

        // 为了记录每个桶中，实际存放了多少个数据，我们定义一个一维数组来记录各个桶的每次放入的数据个数
        int[] bucketElementCounts = new int[10];
        for (int i = 0,n=1; i < maxLength; i++,n*=10) {
            // 针对每个元素对应的位进行排序 第一次个位，第二次十位，第三次百位
            for (int j = 0; j < arr.length; j++) {
                // 取出每个元素的个数
                int digitOfElement = arr[j] /n % 10;
                // 放入到对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }

            // 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来的数组）
            int index = 0;
            // 遍历每一个桶，并将桶中的数据放入到原数组
            for (int k = 0; k < bucketElementCounts.length; k++) {
                // 如果桶中有数据才放入到原数组
                if (bucketElementCounts[k]!=0){
                    // 循环该桶即第K个桶（第k个一维数组
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        // 取出元素放入arr
                        arr[index++] = bucket[k][l];
                    }
                }
                // 第一轮处理后，需要将每个bucketElementCounts[k]置零
                // 为了下一轮的元素添加
                // 这样就不需要清除二维数组中的数据了
                // 因为bucketElementCounts[k]就代表了各个桶中有没有数据，有多少个数据
                // 上一轮的剩余数据无视即可
                bucketElementCounts[k]=0;
            }

//            System.out.println("第x轮对个位的排序处理" + Arrays.toString(arr));
        }

        /*
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的个数
            int digitOfElement = arr[j] /1 % 10;
            // 放入到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }

        // 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来的数组）
        int index = 0;
        // 遍历每一个桶，并将桶中的数据放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            // 如果桶中有数据才放入到原数组
            if (bucketElementCounts[k]!=0){
                // 循环该桶即第K个桶（第k个一维数组
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素放入arr
                    arr[index++] = bucket[k][l];
                }
            }
            // 第一轮处理后，需要将每个bucketElementCounts[k]置零
            // 为了下一轮的元素添加
            // 这样就不需要清除二维数组中的数据了
            // 因为bucketElementCounts[k]就代表了各个桶中有没有数据，有多少个数据
            // 上一轮的剩余数据无视即可
            bucketElementCounts[k]=0;
        }

        System.out.println("第一轮对个位的排序处理" + Arrays.toString(arr));


        // 第二轮排序（针对每个元素的个位进行排序处理）
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的十位
            int digitOfElement = arr[j] /10 % 10;
            // 放入到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }

        // 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来的数组）
        index = 0;
        // 遍历每一个桶，并将桶中的数据放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            // 如果桶中有数据才放入到原数组
            if (bucketElementCounts[k]!=0){
                // 循环该桶即第K个桶（第k个一维数组
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素放入arr
                    arr[index++] = bucket[k][l];
                }
            }
            bucketElementCounts[k]=0;
        }

        System.out.println("第二轮对个位的排序处理" + Arrays.toString(arr));

        // 第三轮排序（针对每个元素的百位进行排序处理）
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的百位
            int digitOfElement = arr[j] /100 % 10;
            // 放入到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }

        // 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来的数组）
        index = 0;
        // 遍历每一个桶，并将桶中的数据放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            // 如果桶中有数据才放入到原数组
            if (bucketElementCounts[k]!=0){
                // 循环该桶即第K个桶（第k个一维数组
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素放入arr
                    arr[index++] = bucket[k][l];
                }
            }
        }

        System.out.println("第三轮对个位的排序处理" + Arrays.toString(arr));
        */
    }
}

