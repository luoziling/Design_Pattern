package priv.wzb.leet_code_problems.leetcode.editor.cn;
//地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一
//格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但
//它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？
//
//
//
// 示例 1：
//
// 输入：m = 2, n = 3, k = 1
//输出：3
//
//
// 示例 2：
//
// 输入：m = 3, n = 1, k = 0
//输出：1
//
//
// 提示：
//
//
// 1 <= n,m <= 100
// 0 <= k <= 20
//
// Related Topics 深度优先搜索 广度优先搜索 动态规划 👍 383 👎 0

class JiQiRenDeYunDongFanWeiLcof{
	/*
package priv.wzb.leet_code_problems.leetcode.editor.cn;
${question.content}
class $!velocityTool.camelCaseName(${question.titleSlug}){
	public static void main(String[] args) {
		Solution solution = new $!velocityTool.camelCaseName(${question.titleSlug})().new Solution();

	}
${question.code}
}
	 */
	public static void main(String[] args) {
		Solution solution = new JiQiRenDeYunDongFanWeiLcof().new Solution();
		solution.movingCount(1,2,1);
	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
	int res = 0;
	boolean[][] visited;
	/**
	 * 机器人访问
	 * dfs 并且向四个方向扩展遍历
	 * 函数编写计算i的单个之和
	 * 注意点：注意已访问过的不要重复访问 同时防止循环访问
	 * @param m 最大行
	 * @param n 最大列
	 * @param k 行列最大和
	 * @return
	 */
    public int movingCount(int m, int n, int k) {
		visited = new boolean[m][n];
    	dfs(m,n,0,0,k);
    	return res;
    }

	/**
	 *
	 * @param m
	 * @param n
	 * @param i 当前所在位置
	 * @param j 当前所在位置
	 * @param k
	 */
	private void dfs(int m, int n, int i, int j, int k) {
		// 越界判断
		if (i<0 || i>=m || j<0||j>=n || (xSum(i) + xSum(j))>k){
			return;
		}
		if (!visited[i][j]){
			res++;
			visited[i][j] = true;
		}else {
			return;
		}
		dfs(m,n,i,j+1,k);
		dfs(m,n,i,j-1,k);
		dfs(m,n,i-1,j,k);
		dfs(m,n,i+1,j,k);

	}

	private int xSum(int x){
		int res = 0;
		while (x != 0){
			res += x%10;
			x/=10;
		}
		return res;
	}
}
//leetcode submit region end(Prohibit modification and deletion)

}
