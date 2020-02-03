package priv.wzb.leet_code.dynamic_programming.minimum_path_sum_64;

/**
 * @author Satsuki
 * @time 2019/12/4 22:47
 * @description:
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 *
 * 说明：每次只能向下或者向右移动一步。
 *
 * 示例:
 *
 * 输入:
 * [
 *   [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-path-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 只能向下或者向右走
 * 动态规划
 * 原问题：求左上到右下的最短路径->现问题：到达每一个坐标的最短路径，最后返回右下坐标的路径即可
 * 边界：第0行的最短路径是固定的
 * dp转化算式：dp[i][j] = Math.min(dp[i-1][j],dp[i],[j-1]) + grid[i][j]
 */
public class Solution {
    public int minPathSum(int[][] grid) {
        // 判断越界
        if (grid.length == 0){
            return 0;
        }
        
        // 动态规划的dp二维数组
        int[][] dp = new int[grid.length][grid[0].length];
        // 初始化边界
        dp[0][0] = grid[0][0];
        for (int i = 1; i < grid[0].length; i++) {
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }
        
        // 求解dp
        for (int i = 1; i < grid.length; i++) {
            // 列上初始化
            dp[i][0] = dp[i-1][0] + grid[i][0];
            // dp[i][j]代表的是中间的是像行一样向前推进的
            for (int j = 1; j < grid[0].length; j++) {
                dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[grid.length-1][grid[0].length-1];
    }
}
