package priv.wzb.leet_code.array.toeplitz_matrix;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/22 20:13
 * @since 1.0.0
 */
public class Solution {
    public boolean isToeplitzMatrix(int[][] matrix) {
        // 对角线，遍历，与左上角对比
        int m = matrix.length;int n = matrix[0].length;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i-1][j-1] != matrix[i][j]){
                    return false;
                }
            }

        }
        return true;
    }
}
