package priv.wzb.datastructure.algorithm.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/1 14:33
 * @description:
 * 二分查找
 * 二分查找必须是有序的
 */
public class BinarySearch {
    public static void main(String[] args) {
        int arr[] = {1,8,10,89,1000,1000,1000,1234};

        int resIdex = binarySearch(arr,0,arr.length,99);
        List<Integer> list = binarySearch2(arr, 0, arr.length, 1010);
        System.out.println("redIndex = " + list);
    }

    // 二分查找算法

    /**
     *
     * @param arr 数组
     * @param left 左侧索引
     * @param right 右侧索引
     * @param findVal 要查找的值
     * @return 找到返回下标找不到返回-1
     */
    public static int binarySearch(int[] arr, int left,int right,int findVal){
        // 当left>right，说明递归完整个数组还未存在
        if (left>right){
            return -1;
        }
        int mid = (left+right)/2;
        int midVal = arr[mid];
        if (findVal>midVal){
            // 向右递归
            return binarySearch(arr,mid+1,right,findVal);
        } else if (findVal<midVal) {
            // 向左递归
            return binarySearch(arr,left,mid-1,findVal);
        }else {
            return mid;
        }
    }

    // 如果要查找的数值是多个就返回一个数组
    /**
     * 找到mid索引不要马上返回
     * 将mid索引加入list，向左继续扫描，找到相同的加入list
     * 向右继续扫描，找到相同的加入list
     */
    public static List<Integer> binarySearch2(int[] arr, int left, int right, int findVal){
        // 当left>right，说明递归完整个数组还未存在
        if (left>right){
            return new ArrayList<Integer>();
        }
        int mid = (left+right)/2;
        int midVal = arr[mid];
        if (findVal>midVal){
            // 向右递归
            return binarySearch2(arr,mid+1,right,findVal);
        } else if (findVal<midVal) {
            // 向左递归
            return binarySearch2(arr,left,mid-1,findVal);
        }else {
//            return mid;
            List<Integer> resIndexList = new ArrayList<>();
            // 向mid索引值的左边扫描
            int temp = mid-1;
            while (true){
                if (temp<0 || arr[temp] != findVal){
                    // 退出
                    break;
                }
                // 否则就将temp加入集合
                resIndexList.add(temp);
                temp = -1; // temp左移
            }
            resIndexList.add(mid);

            // 向右扫描
            temp = mid+1;
            while (true){
                if (temp>arr.length - 1 || arr[temp] != findVal){
                    // 因为是有序数组，所以直接相邻数字才可能是相同的
                    // 退出
                    break;
                }
                // 否则就将temp加入集合
                resIndexList.add(temp);
                temp += 1; // temp右移

            }
            return resIndexList;
        }
    }
}
