package priv.wzb.leet_code.dynamic_programming;

/**
 * offer_47
 *
 * @author yuzuki
 * @date 2021/8/8 22:14
 * @description:
 * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
 *
 *  
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @since 1.0.0
 */
public class offer_47 {
    public int maxValue(int[][] grid) {
        // 动态规划
        // 定义 dp[i][j] 代表某一个最高的价值
        // 初始化dp[0][0] = grid[0][0] 第一行只能从左边走，第一列只能从上到下
        // 状态转移方程 dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]) + grid[i][j];
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        // 初始化
        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }
}
