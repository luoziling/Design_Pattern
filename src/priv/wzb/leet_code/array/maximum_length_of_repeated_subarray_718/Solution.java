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
 */
public class Solution {
    public int findLength(int[] A, int[] B) {
        int maxLength = 0;
        for (int i = 0; i < A.length; i++) {


            for (int j = 0; j < B.length; j++) {
                int length = 0;
                int k = i;
                int z = j;
                while (k<A.length&&z<B.length&&A[k]==B[z]){
                    length++;
                    k++;
                    z++;
                }
                if (length>maxLength){
                    maxLength = length;
                }
            }
        }
        return maxLength;
    }

    //[0,1,1,1,1]
    //[1,0,1,0,1]
    public static void main(String[] args) {
        int[] A = {0,1,1,1,1};
        int[] B = {1,0,1,0,1};
        System.out.println(new Solution().findLength(A,B));
    }
}
