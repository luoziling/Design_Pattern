package priv.wzb.datastructure.algorithm.search;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/1 15:25
 * @description:
 */
public class FibonacciSearch {
    public static int maxSize=20;
    public static void main(String[] args) {
        int[] arr = {1,8,10,89,1000,1234};

        System.out.println(fibSearch(arr,1));

    }

    // 因为后面我们mid = low + F(k-1)-1,需要用到斐波那契数列因此我们需要先获取一个斐波那契数列
    // 非递归得到一个斐波那契数列
    public static int[] fib(){
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i-1] + f[i-2];
        }
        return f;
    }

    // 编写斐波那契数列查找算法

    /**
     *
     * @param a 数组
     * @param key 我们需要查找的关键码
     * @return 返回下标没有返回-1
     * 斐波那契查找其实本质上也是一个空间换时间
     * 每次都维护了一个足够大的斐波那契数列
     * mid的值由最简单的中间值慢慢的转变
     * 斐波那契查找运用了黄金分割比例0.618作为mid值设定的基准确定0.618代表的具体值则是使用了斐波那契数列
     * 因为斐波那契数列的两个值相比较近似于黄金分割0.618
     */
    public static int fibSearch(int[] a,int key){
        // 非递归
        int low = 0;
        int high = a.length-1;
        int k = 0; // 表示斐波那契数列分割数值的下标
        int mid = 0; // 存放mid
        int[] f = fib();
        // 获取斐波那契数列分割数值的下标
        // 根据数组长度来确定分割数值的下标
        while (high>f[k]-1){
            k++;
        }
        // 因为f[k]值可能大于a的长度，因此我们需要使用arrays类构造一个新的数组并指向temp[]
        // 不足的部分使用0填充
        // Arrays.copyOf(int[] original,int newLength)复制指定的数组，用零截取或填充（如有必要），以便复制具有指定的长度。
        // 对于原始数组和副本都有效的所有索引，两个数组将包含相同的值。
        // 对于在副本中而不是原件有效的任何索引，副本将包含0 。
        // 当且仅当指定长度大于原始数组的长度时，这些索引才会存在。
        int[] temp = Arrays.copyOf(a,f[k]);
        // 实际上需要使用a数组的最后数填充temp
        // 举例：
        // temp = {1,8,10,89,1000,1234,0,0,0} =>{1,8,10,89,1000,1234,1234,1234,1234}
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = a[high];
        }

        // 使用while循环处理，找到我们的数key
        while (low<=high){
            // 不论向左还是向右都跟着黄金分割比例来获取mid值
            // 只要这个条件满足就找
            mid = low + f[k-1]-1;
            if (key<temp[mid]){
                // 向左查找
                high = mid-1;
                // 全部元素 = 前面的元素+后面的元素
                // f[k] = f[k-1] + f[k-2]
                // 因为前面有f[k-1]个元素，所以可以继续拆分 f[k-1] = f[k-2] + f[k-3]
                // 即在f[k-1]的前面继续查找 k--
                // 即下次循环mid = f[k-1-1] -1
                k--;
            }else if (key>temp[mid]){
                // 向右查找
                low = mid+1;
                k -= 2;
            }else {
                // 需要确定返回的是哪个下标
                if (mid<=high){
                    return  mid;
                }else {
                    return high;
                }
            }
        }

        return -1;
    }
}
