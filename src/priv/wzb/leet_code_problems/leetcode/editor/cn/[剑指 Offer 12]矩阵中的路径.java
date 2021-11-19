package priv.wzb.leet_code_problems.leetcode.editor.cn;
//给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
//
//
//
// 例如，在下面的 3×4 的矩阵中包含单词 "ABCCED"（单词中的字母已标出）。
//
//
//
//
//
// 示例 1：
//
//
//输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word =
//"ABCCED"
//输出：true
//
//
// 示例 2：
//
//
//输入：board = [["a","b"],["c","d"]], word = "abcd"
//输出：false
//
//
//
//
// 提示：
//
//
// 1 <= board.length <= 200
// 1 <= board[i].length <= 200
// board 和 word 仅由大小写英文字母组成
//
//
//
//
// 注意：本题与主站 79 题相同：https://leetcode-cn.com/problems/word-search/
// Related Topics 数组 回溯 矩阵 👍 454 👎 0

class JuZhenZhongDeLuJingLcof {
	public static void main(String[] args) {
		Solution solution = new JuZhenZhongDeLuJingLcof().new Solution();
		char[][] board = {{'a','a'}};
		solution.exist(board,"aaa");
	}

	//leetcode submit region begin(Prohibit modification and deletion)
	class Solution {
		/**
		 * 探索矩阵中是否能找到某个单词
		 * dfs+从每个点开始遍历查找
		 * 从上下左右四个方向去找，找到符合的就是true
		 * 注意已访问过的不能重复访问同时其他遍历要能访问 通过回溯算法解决
		 * @param board
		 * @param word
		 * @return
		 */
		public boolean exist(char[][] board, String word) {
			// 思路：图的深度搜索
			for (int i = 0; i < board.length; i++) {
				for(int j = 0;j<board[0].length;j++){
					if (dfs(board, word,0,i,j)){
						// 提前中止
						return true;
					}
				}
			}
			return false;
		}

		private boolean dfs(char[][] board, String word, int pos, int i, int j) {
			if (pos == word.length()) {
				return true;
			}
			// 越界直接false
			if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
				return false;
			}
			if (board[i][j] == word.charAt(pos)) {
				// 当前字符相等 匹配下一个字符
				pos++;
			} else {
				// 否则当前无法继续直接false
				return false;
			}
			// 从矩阵每个位置开始搜索 朝着上下左右四个方向，匹配则继续
			// 需要访问数组 不能回溯循环遍历 借助回溯来避免
			// 访问过当前位置，将当前位置置为不可访问的 但是其他方向访问需要回溯
			char rollbackChar = board[i][j];

			board[i][j] = '\0';
			boolean res = dfs(board, word, pos, i, j - 1) ||
					dfs(board, word, pos, i, j + 1) ||
					dfs(board, word, pos, i-1, j) ||
					dfs(board, word, pos, i+1, j);
			board[i][j] = rollbackChar;
			return res;
		}
	}
//leetcode submit region end(Prohibit modification and deletion)

}
