package priv.wzb.leet_code.dynamic_programming.offer_49;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/8/12 23:26
 * @description:
 * 我们把只包含质因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第 n 个丑数。
 * @since 1.0.0
 */
public class Solution {
    public int nthUglyNumber(int n) {
        // 分析 使用dp，丑数的下一个数字总是与上一个数字有关
        // 设2a,3b,5c为丑数下一个数字，随着丑数推移，abc不断递增，以满足下一个数字x永远在2a/3b/5c与 2a-1/3b-1/5c-1之间
        // 下一步依赖上一步 动态规划
        // 定义dp[i-1]代表第i个丑数
        // 边界 dp[0] = 1
        // 状态转移方程：dp[i] = Math.min(2a,3b,5c)
        int[] dp = new int[n];
        dp[0] = 1;
        int a = 0,b=0,c=0;
        for (int i = 1; i < n; i++) {
            int n2 = dp[a]*2,n3 = dp[b]*3,n5=dp[c]*5;
            dp[i] = Math.min(Math.min(n2,n3),n5);
            if (dp[i] == n2){
                // 选择了a
                a++;
            }
            if (dp[i] == n3){
                // 选择了b
                b++;
            }
            if (dp[i] == n5){
                // 选择了c
                c++;
            }
        }

        return dp[n-1];
    }
}
