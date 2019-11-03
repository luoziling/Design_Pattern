package priv.wzb.datastructure.tree;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/2 18:07
 * @description:
 */
public class HeapSort {
    public static void main(String[] args) {
//        int arr[] = {4,6,8,5,9};
//        // 升序--》大顶堆
//        heapSort(arr);

        long now = System.currentTimeMillis();
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int)(Math.random() * 80000000);// 生成一个[0,8000000]的随机数
        }
        heapSort(arr);
        long last = System.currentTimeMillis();
        System.out.println("花费时间：" + (last-now));
//        System.out.println(Arrays.toString(arr));
        System.out.println(arr.length);
    }

    // 编写一个堆排序的方法
    public static void heapSort(int arr[]){
        int temp = 0;
        System.out.println("堆排序");
        // 分布完成
        /**
        adjustHeap(arr,1,arr.length);
        System.out.println("first" + Arrays.toString(arr));//4,9,8,5,6

        adjustHeap(arr,0,arr.length);
        System.out.println("sec" + Arrays.toString(arr));//9,6,8,5,1
         **/
        // 最终代码
        // 将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
        for (int i = arr.length/2 -1; i >= 0; i--) {
            adjustHeap(arr,i,arr.length);
        }


        // 将堆顶元素与末尾交换，最大元素沉底
        // 多次循环每次都是最大元素沉底
        // 类似选择排序每次挑选一个最小/最大放到合适的位置
        // 重构满足堆定义
        // 反复执行，直到有序
        for (int j = arr.length-1; j > 0; j--) {
            // 交换
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;

            adjustHeap(arr,0,j);
        }

//        System.out.println("arr=" + Arrays.toString(arr));
    }

    // 将一个数组(二叉树），调整为一个大顶堆

    /**
     *功能：将以i对应的非叶子节点的树调整成大顶堆
     * 举例：int arr[] = {4,6,8,5,9};==> {4,9,8,5,6}
     * @param arr 待调整的数组
     * @param i 非叶子节点在数组中的索引
     * @param length 对多少个元素进行调整，length在逐渐减少
     */
    public static void adjustHeap(int arr[],int i,int length){
        int temp = arr[i]; // 先取出当前元素的值保存在零时变量
        // 开始调整
        //  k = i*2+1 k指向i节点的左子节点
        for (int k = i*2+1; k < length; k=k*2+1) {
            if (k+1<length && arr[k]<arr[k+1]){
                // 左子节点小于右子节点
                k++; // k指向右子节点（较大值）
            }
            if (arr[k]>temp){
                // 如果子节点大于父节点
                // 把较大的值赋给当前节点
                arr[i] = arr[k];
                // i指向k继续循环比较
                i=k;
            }else {
                // 因为这是第一个非叶子节点，从下至上调整所以可以直接break；
                break;
            }
        }

        // 当for循环结束后，我们已经将以i为父节点的树的最大值，放在了最顶（局部）
        arr[i] = temp;


    }
}
