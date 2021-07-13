package priv.wzb.leet_code.dynamic_programming.distinct_subsequences_115;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-03-17 20:13
 * @description:
 **/

public class Solution {
	public int numDistinct(String s, String t) {
		// 思路 遍历t的字符找到在s中的位置，直到能找到玩完整的t
		// 动态规划，dp 初始 状态转移方程
		// 初始为 t为空则必为1 s为空 比为0
		// 状态转移方程分两种情况看代，如果当前字符相等则继续比较，
		int m = s.length(),n = t.length();
		if (m<n){
			return 0;
		}
		int[][] dp = new int[m+1][n+1];
		// 初始化,如果t的子序列为空则必然为s的子序列
		for (int i = 0; i <= m; i++) {
			dp[i][n] = 1;
		}
		// 倒着求解
		for (int i = m-1; i >= 0; i--) {
			char sChar = s.charAt(i);
			for (int j = n-1; j >= 0; j--) {
				char tChar = t.charAt(j);
				if (sChar == tChar){
					// 当前匹配 统计是否上一个也匹配，或者忽视当前s子字符也可达成匹配，如果可以就达成多种情况
					dp[i][j] = dp[i+1][j+1] + dp[i+1][j];
				}else {
					// 当前不匹配，查看是否与上一次匹配，匹配的话则可删除当前字符还是达成匹配
					dp[i][j] = dp[i+1][j];
				}
			}

		}
		return dp[0][0];

	}
}
