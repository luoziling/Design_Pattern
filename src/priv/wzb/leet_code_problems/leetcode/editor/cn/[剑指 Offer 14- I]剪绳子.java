package priv.wzb.leet_code_problems.leetcode.editor.cn;
//给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m-1] 。
//请问 k[0]*k[1]*...*k[m-1] 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18
//。
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
// 提示：
//
//
// 2 <= n <= 58
//
//
// 注意：本题与主站 343 题相同：https://leetcode-cn.com/problems/integer-break/
// Related Topics 数学 动态规划 👍 322 👎 0

class JianShengZiLcof{
	public static void main(String[] args) {
		Solution solution = new JianShengZiLcof().new Solution();

	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int cuttingRope(int n) {
//    	if (n<=3){
//    		return n-1;
//		}
//    	int res = 1;
//    	while (n>4){
//    		res *=3;
//    		n-=3;
//		}
//    	if (n>0){
//			res *= n;
//		}
//
//    	return res;
		// 动态规划
		// dp[i] 绳子在i这个位置的最优解
		// 边界：0和1无法切分答案为0
		// dp方程:j为[1,i)在j每个位置都可以选择分割，分割后的最大结果为j位置与后面乘积或者后面位置还要分割的最大值的乘积
		int[] dp = new int[n+1];
		for(int i = 2;i<n+1;i++){
			int curMax = 0;
			for(int j=1;j<i;j++){
				curMax = Math.max(curMax,Math.max(j*(i-j),j*dp[i-j]));
			}
			dp[i] = curMax;
		}
		return dp[n];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}
