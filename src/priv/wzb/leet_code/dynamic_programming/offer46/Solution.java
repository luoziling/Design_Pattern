package priv.wzb.leet_code.dynamic_programming.offer46;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/8/4 22:20
 * @description:
 * @since 1.0.0
 */
public class Solution {
    public int translateNum(int num) {
        // 数字转换，遍历数字，动态规划，当前情况的选择依赖于前面的情况
        // dp[i]代表第i个数字时的选择数量
        // 初始值：dp[1] = 1 由下面方程可得dp[0] = 1以避免dp[2]为可组合的情况
        // 状态转移方程：dp[i] = dp[i-1]+dp[i-2] (当前组合在[10,25]之内)否则 当前无法创造新情况dp[i] = dp[i-1]
        String str = String.valueOf(num);
        if (str.length()<2){
            return 1;
        }
        // 初始化
        int[] dp = new int[str.length()+1];
        dp[0] = 1;
        dp[1] = 1;
        char[] chars = str.toCharArray();
        for (int i = 2; i <= chars.length; i++) {
            int num1 = 10 * (chars[i - 2] - '0') + (chars[i-1] - '0');
            if ( num1>9 && num1<26){
                dp[i] = dp[i-1]+dp[i-2];
            }else {
                dp[i] = dp[i-1];
            }
        }
        return dp[dp.length-1];

    }

    public static void main(String[] args) {
        int i = new Solution().translateNum(25);
        System.out.println("i = " + i);
    }
}
