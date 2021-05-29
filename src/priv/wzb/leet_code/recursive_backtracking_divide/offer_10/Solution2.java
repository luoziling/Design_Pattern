package priv.wzb.leet_code.recursive_backtracking_divide.offer_10;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 21:42
 * @description:
 **/

public class Solution2 {
	public int fib(int n) {
		if (n == 0){
			return 0;
		}
		if (n == 1){
			return 1;
		}
		int[] dp = new int[n+1];
		// 因为一共计算n-1次然而实际上要从第3次开始算，因此多算了一次
		for (int i = 0; i <= n; i++) {
			if (i == 0){
				dp[i] = 0;
				continue;
			}
			if (i == 1){
				dp[i] = 1;
				continue;
			}
			dp[i] = (dp[i-1] + dp[i-2])%1000000007;
		}
		return dp[n];
	}
}
