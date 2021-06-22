package priv.wzb.leet_code.slidingwindow.flipping_an_image_832;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/24 21:07
 * @since 1.0.0
 */
public class Solution {
    public int[][] flipAndInvertImage(int[][] A) {
        int exchange = 0;
        for (int i = 0; i < A.length; i++) {
            int[] nowArray = A[i];
            for (int i1 = 0; i1 < nowArray.length/2; i1++) {
                exchange = nowArray[i1];
                nowArray[i1] = nowArray[nowArray.length-i1-1];
                nowArray[nowArray.length-i1-1] = exchange;
            }
        }
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A[i][j] = A[i][j]^1;
            }
        }
        return A;
    }
}
