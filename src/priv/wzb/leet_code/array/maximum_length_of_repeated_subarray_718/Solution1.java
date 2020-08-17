package priv.wzb.leet_code.array.maximum_length_of_repeated_subarray_718;

/**
 * @author Satsuki
 * @time 2020/7/1 11:27
 * @description:
 * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
 *
 * 示例 1:
 *
 * 输入:
 * A: [1,2,3,2,1]
 * B: [3,2,1,4,7]
 * 输出: 3
 * 解释:
 * 长度最长的公共子数组是 [3, 2, 1]。
 * 说明:
 *
 * 1 <= len(A), len(B) <= 1000
 * 0 <= A[i], B[i] < 100
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 滑动窗口
 */
public class Solution1 {
    public int findLength(int[] A, int[] B) {
        int n = A.length,m = B.length;
        int ret=0;
        for (int i = 0; i < n; i++) {
            int len = Math.min(m,n-i);
            int maxlen = maxLength(A,B,i,0,len);
            ret = Math.max(ret,maxlen);
        }
        for (int i = 0; i < m; i++) {
            int len = Math.min(m-i,n);
            int maxlen = maxLength(A,B,0,i,len);
            ret = Math.max(maxlen,ret);
        }
        return ret;
    }

    public int maxLength(int[] A,int[] B,int addA,int addB,int len){
        int ret = 0,k=0;
        for (int i = 0; i < len; i++) {
            if (A[addA + i] == B[addB + i]){
                k++;
            }else {
                k=0;
            }
            ret = Math.max(ret,k);
        }
        return ret;
    }

    //[0,1,1,1,1]
    //[1,0,1,0,1]
    public static void main(String[] args) {
        int[] A = {0,1,1,1,1};
        int[] B = {1,0,1,0,1};
        System.out.println(new Solution1().findLength(A,B));
    }
}
