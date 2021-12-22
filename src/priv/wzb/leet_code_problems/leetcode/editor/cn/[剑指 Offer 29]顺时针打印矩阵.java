package priv.wzb.leet_code_problems.leetcode.editor.cn;
//输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
//
//
//
// 示例 1：
//
// 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
//输出：[1,2,3,6,9,8,7,4,5]
//
//
// 示例 2：
//
// 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
//输出：[1,2,3,4,8,12,11,10,9,5,6,7]
//
//
//
//
// 限制：
//
//
// 0 <= matrix.length <= 100
// 0 <= matrix[i].length <= 100
//
//
// 注意：本题与主站 54 题相同：https://leetcode-cn.com/problems/spiral-matrix/
// Related Topics 数组 矩阵 模拟 👍 316 👎 0

class ShunShiZhenDaYinJuZhenLcof{
	public static void main(String[] args) {
		Solution solution = new ShunShiZhenDaYinJuZhenLcof().new Solution();

	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] spiralOrder(int[][] matrix) {
    	if (matrix.length == 0){
    		return new int[0];
		}
    	int[][] posArray = {
				{0,1},
				{1,0},
				{0,-1},
				{-1,0}
		};
		int m = matrix.length;
		int n = matrix[0].length;
		int totalLength = m*n;
    	int[] res = new int[totalLength];
    	int index = 0;
    	int posIndex = 0;
    	int[] pos = posArray[posIndex];
    	int matrixX = 0;
    	int matrixY = 0;
    	boolean[][] visited = new boolean[m][n];
    	while (index<totalLength){
    		res[index++] = matrix[matrixX][matrixY];
    		visited[matrixX][matrixY] = true;
    		if (matrixX + pos[0]<0 || matrixX + pos[0]>=m || matrixY+pos[1]<0 || matrixY+pos[1]>=n || visited[matrixX + pos[0]][matrixY+pos[1]]){
    			posIndex = (posIndex+1)%4;
    			pos = posArray[posIndex];
			}
    		matrixX+=pos[0];
    		matrixY+=pos[1];
		}
    	return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}
