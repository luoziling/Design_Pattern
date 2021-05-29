package priv.wzb.leet_code.dynamic_programming.offer_14;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-28 21:28
 * @description:
 * 给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m-1] 。请问 k[0]*k[1]*...*k[m-1] 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
 *
 * 示例 1：
 *
 * 输入: 2
 * 输出: 1
 * 解释: 2 = 1 + 1, 1 × 1 = 1
 * 示例 2:
 *
 * 输入: 10
 * 输出: 36
 * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36
 * 提示：
 *
 * 2 <= n <= 58
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/jian-sheng-zi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 思路
 * dp定义 dp【i】 = 在绳子长度为i时的最大乘积
 * 边界值 i = 2时只能切为两段，因此 结果为1*1 = 2
 * 状态转移方程 max[i] = Math.max(max[i-1],Math.max(j * (i-j),j * dp[i-j]))
 **/

public class Solution {
	public int cuttingRope(int n) {
		int[] dp = new int[n+1];
		dp[2] = 1;
		// 从3开始剪切
		for (int i = 3; i < n+1; i++) {
			// 从2开始尝试剪 ，在j处先剪一次
			for (int j = 2; j < i; j++) {
				// 最大长度等于此次剪1 就等于上一次乘积/ 在j处不剪/剪
				dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));
			}
		}
		return dp[n];
	}
}
