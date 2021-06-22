package priv.wzb.leet_code.array.transpose_matrix_876;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/25 21:33
 * @since 1.0.0
 */
public class Solution {
    public int[][] transpose(int[][] matrix) {
        int m = matrix.length,n = matrix[0].length;
        int[][] res = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[j][i] = matrix[i][j];
            }
        }
        return res;
    }
}
