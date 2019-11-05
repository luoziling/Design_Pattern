package priv.wzb.datastructure.algorithm.search;

/**
 * @author Satsuki
 * @time 2019/11/4 20:38
 * @description:
 */
public class BinarySearchNoRecursion {
    public static void main(String[] args) {
        int arr[] = {1,3,8,10,11,67,100};
        int index = binarySearch(arr,-8);
        System.out.println("index = " + index);
    }

    // 二分查找的非递归实现

    /**
     *
     * @param arr 待查找数组 升序
     * @param target 要查找的数
     * @return 下标
     */
    public static int binarySearch(int[] arr,int target){
        int left = 0;
        int right = arr.length;
        while (left<=right){
            int mid = (left+right)/2;
            if (arr[mid] == target){
                return mid;
            }else if (arr[mid] > target){
                // 向左查找
                right = mid-1;
            }else {
                // 向右查找
                left = mid+1;
            }
        }
        return -1;
    }
}
