package priv.wzb.leet_code_problems.leetcode.editor.cn;
//一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
//
// 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
//
// 示例 1：
//
// 输入：n = 2
//输出：2
//
//
// 示例 2：
//
// 输入：n = 7
//输出：21
//
//
// 示例 3：
//
// 输入：n = 0
//输出：1
//
// 提示：
//
//
// 0 <= n <= 100
//
//
// 注意：本题与主站 70 题相同：https://leetcode-cn.com/problems/climbing-stairs/
//
//
// Related Topics 记忆化搜索 数学 动态规划 👍 215 👎 0

import java.util.HashMap;
import java.util.Map;

class QingWaTiaoTaiJieWenTiLcof{
	public static void main(String[] args) {
		Solution solution = new QingWaTiaoTaiJieWenTiLcof().new Solution();

	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
	/**
	 * map 加速搜索
	 * dfs+剪枝
	 */
	public Map<Integer,Integer> accelerateMap = new HashMap<>(4);
    public int numWays(int n) {
    	// 斐波那契 f0=1 f1=1
		if (n == 0){
			return 1;
		}
		if (n == 1){
			return 1;
		}
		if (accelerateMap.containsKey(n)){
			return accelerateMap.get(n);
		}else {
			int f0 = (numWays(n-1)%1000000007);
			int f1 = (numWays(n-2)%1000000007);
			int res = (f0+f1)%1000000007;
			accelerateMap.put(n,res);
			return res;
		}
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}
