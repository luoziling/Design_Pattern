package priv.wzb.leet_code_problems.leetcode.editor.cn;
//给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m - 1]
// 。请问 k[0]*k[1]*...*k[m - 1] 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘
//积是18。
//
// 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
//
//
//
// 示例 1：
//
// 输入: 2
//输出: 1
//解释: 2 = 1 + 1, 1 × 1 = 1
//
// 示例 2:
//
// 输入: 10
//输出: 36
//解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36
//
//
//
// 提示：
//
//
// 2 <= n <= 1000
//
//
// 注意：本题与主站 343 题相同：https://leetcode-cn.com/problems/integer-break/
// Related Topics 数学 动态规划 👍 142 👎 0

class JianShengZiIiLcof{
	public static void main(String[] args) {
		Solution solution = new JianShengZiIiLcof().new Solution();

	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int cuttingRope(int n) {
    	// dp  绳子每次可分为切割/不切割
		int[] dp = new int[n+1];
		dp[0] = dp[1] = 0;
		for (int i = 2; i < n + 1; i++) {
			int curMax = 0;
			for (int j = 1; j < i; j++) {
				int cutOnce = (j * (i - j))%1000000007;
				int cutMulti = j*dp[i-j]%1000000007;
//				int cutOnce = (j * (i - j));
//				int cutMulti = j*dp[i-j];
				curMax = Math.max(curMax,Math.max(cutOnce,cutMulti));
			}
			dp[i] = curMax%1000000007;
		}
		return dp[n];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}
