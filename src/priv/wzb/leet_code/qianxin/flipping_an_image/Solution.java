package priv.wzb.leet_code.qianxin.flipping_an_image;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/9 20:17
 * @description:
 * 给定一个二进制矩阵 A，我们想先水平翻转图像，然后反转图像并返回结果。
 *
 * 水平翻转图片就是将图片的每一行都进行翻转，即逆序。例如，水平翻转 [1, 1, 0] 的结果是 [0, 1, 1]。
 *
 * 反转图片的意思是图片中的 0 全部被 1 替换， 1 全部被 0 替换。例如，反转 [0, 1, 1] 的结果是 [1, 0, 0]。
 *
 * 示例 1:
 *
 * 输入: [[1,1,0],[1,0,1],[0,0,0]]
 * 输出: [[1,0,0],[0,1,0],[1,1,1]]
 * 解释: 首先翻转每一行: [[0,1,1],[1,0,1],[0,0,0]]；
 *      然后反转图片: [[1,0,0],[0,1,0],[1,1,1]]
 * 示例 2:
 *
 * 输入: [[1,1,0,0],[1,0,0,1],[0,1,1,1],[1,0,1,0]]
 * 输出: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
 * 解释: 首先翻转每一行: [[0,0,1,1],[1,0,0,1],[1,1,1,0],[0,1,0,1]]；
 *      然后反转图片: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
 * 说明:
 *
 * 1 <= A.length = A[0].length <= 20
 * 0 <= A[i][j] <= 1
 * 数组大小限制并且是个正方形二维矩阵
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/flipping-an-image
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class Solution {
    public int[][] flipAndInvertImage(int[][] A) {
//        LinkedList<Integer> linkedList = new LinkedList<>();
        // 翻转
        // 反转
        int temp;
        for(int[] a:A){
            for (int i = 0; i < a.length / 2; i++) {
                // 逆序
                temp = a[i];
                a[i] = a[a.length-i-1];
                a[a.length-i-1] = temp;
            }
            // 反转
            for (int i = 0; i < a.length; i++) {
                a[i] = (a[i]^1);
            }
//            System.out.println(Arrays.toString(a));
        }
        return A;
    }

    public static void main(String[] args) {
        int[][] A = new int[][]{{1,1,0,0},{1,0,0,1},{0,1,1,1},{1,0,1,0}};
        int[][] ints = new Solution().flipAndInvertImage(A);
        for(int[] a:ints){
            System.out.println(Arrays.toString(a));
        }
//        System.out.println(Arrays.toString());
    }
}
