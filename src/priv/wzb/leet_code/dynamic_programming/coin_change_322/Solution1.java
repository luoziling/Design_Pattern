package priv.wzb.leet_code.dynamic_programming.coin_change_322;

/**
 * @author Satsuki
 * @time 2019/12/2 21:29
 * @description:
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 *
 * 示例 1:
 *
 * 输入: coins = [1, 2, 5], amount = 11
 * 输出: 3
 * 解释: 11 = 5 + 5 + 1
 * 示例 2:
 *
 * 输入: coins = [2], amount = 3
 * 输出: -1
 * 说明:
 * 你可以认为每种硬币的数量是无限的。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/coin-change
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 动态规划
 * 原状态：求count拆分成最少的数值，这些数值在coins中存在，子状态：把count转为count-coins中的任意一个数值+1就是可拆成的最少数值
 * 边界值:coins中的数值都是边界值
 * 状态转换方程:如果amount减去更合的coin可以得到更少的数值那么就记录这个较少数值的情况
 */
public class Solution1 {
    public int coinChange(int[] coins, int amount) {

        // dp记录0-amount的所有数值的可拆分的最少数值的情况
        int[] dp = new int[amount+1];
        // 初始化dp
        for (int i = 0; i <= amount; i++) {
            dp[i] = -1;

        }
        // 边界值
        dp[0] = 0;
        // 外层循环是指要找amount中解法
        for (int i = 1; i <= amount; i++) {
            // 内层循环代表在coins中寻找
            for (int j = 0; j < coins.length; j++) {
                // 如果符合条件
                // i其实也代表了当前金额，dp[i]就是当前金额的最优解
                // 当i足够减去coins中的某个数值并且减去后的dp对应的下标可表示的最少拆解数值不是初始化值
                if (i-coins[j]>=0&& dp[i-coins[j]]!=-1){
                    // 如果是第一次判定，或者使用amount-coin+1这种方式取得更优解那么就选取更优解
                    if (dp[i] == -1 || dp[i]>dp[i-coins[j]]+1){
                        dp[i] = dp[i-coins[j]]+1;
                    }
                }


            }
        }

        return dp[amount];

    }
}
