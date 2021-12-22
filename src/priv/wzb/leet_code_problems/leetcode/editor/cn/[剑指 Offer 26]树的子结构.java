package priv.wzb.leet_code_problems.leetcode.editor.cn;
//输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
//
// B是A的子结构， 即 A中有出现和B相同的结构和节点值。
//
// 例如:
//给定的树 A:
//
// 3
// / \
// 4 5
// / \
// 1 2
//给定的树 B：
//
// 4
// /
// 1
//返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。
//
// 示例 1：
//
// 输入：A = [1,2,3], B = [3,1]
//输出：false
//
//
// 示例 2：
//
// 输入：A = [3,4,5,1,2], B = [4,1]
//输出：true
//
// 限制：
//
// 0 <= 节点个数 <= 10000
// Related Topics 树 深度优先搜索 二叉树 👍 392 👎 0

class ShuDeZiJieGouLcof{
	public static void main(String[] args) {
		Solution solution = new ShuDeZiJieGouLcof().new Solution();

	}
	class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }
//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isSubStructure(TreeNode A, TreeNode B) {
    	// 注意点：AB都不能为null
    	if (A == null || B == null){
    		return false;
		}
    	boolean nowNodeRes = isSub(A,B);
		boolean leftRes = isSubStructure(A.left, B);
		boolean rightRes = isSubStructure(A.right, B);
		return nowNodeRes || leftRes || rightRes;
	}

	private boolean isSub(TreeNode a, TreeNode b) {
    	// 按照b的相同顺序遍历a,b节点相同则true
    	if (b == null){
    		return true;
		}
    	if (a == null || a.val != b.val){
    		// a遍历结束b还每结束或者过程中不相等
			return false;
		}
		boolean leftRes = isSub(a.left, b.left);
		boolean rightRes = isSub(a.right, b.right);
		return leftRes&&rightRes;
	}
}
//leetcode submit region end(Prohibit modification and deletion)

}
