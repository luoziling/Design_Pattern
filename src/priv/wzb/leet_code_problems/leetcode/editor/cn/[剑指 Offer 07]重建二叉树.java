package priv.wzb.leet_code_problems.leetcode.editor.cn;
//输入某二叉树的前序遍历和中序遍历的结果，请构建该二叉树并返回其根节点。
//
// 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
//
//
//
// 示例 1:
//
//
//Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
//Output: [3,9,20,null,null,15,7]
//
//
// 示例 2:
//
//
//Input: preorder = [-1], inorder = [-1]
//Output: [-1]
//
//
//
//
// 限制：
//
// 0 <= 节点个数 <= 5000
//
//
//
// 注意：本题与主站 105 题重复：https://leetcode-cn.com/problems/construct-binary-tree-from-
//preorder-and-inorder-traversal/
// Related Topics 树 数组 哈希表 分治 二叉树 👍 619 👎 0

import java.util.HashMap;
import java.util.Map;

class ZhongJianErChaShuLcof{
	public static void main(String[] args) {
		Solution solution = new ZhongJianErChaShuLcof().new Solution();
//		int[] preorder = {3,9,20,15,7}, inorder = {9,3,15,20,7};
		int[] preorder = {1,2}, inorder = {1,2};
		solution.buildTree(preorder,inorder);

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
	private Map<Integer,Integer> inorderMap;
	private int preorderIndex = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
    	inorderMap = new HashMap<>(4);
    	for(int i = 0;i<inorder.length;i++){
    		inorderMap.put(inorder[i],i);
		}
		TreeNode res = doBuild(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
		return res;
    }

	private TreeNode doBuild(int[] preorder, int[] inorder, int preorderStart, int preorderEnd, int inorderStart, int inorderEnd) {
    	// 注意点1：判断是否到末尾要双重判断、先序中序都要判断
    	if (preorderStart>preorderEnd || inorderStart>inorderEnd){
    		return null;
		}
    	// 不断遍历先序，构造node
		TreeNode root = new TreeNode(preorder[preorderStart]);
    	// 借助中序来分割定位 先序在中序中 那么每次都可以把中序分为左右两部分 分别是先序根节点的左右子树
		Integer inorderPos = inorderMap.get(preorder[preorderStart]);
		// 注意点2：先序的向前遍历，如果在遍历过程中传参，那么递归过程中的变更对上一层的栈是不可见的，提取出来简单处理
		preorderIndex++;
		root.left = doBuild(preorder,inorder,preorderIndex,preorderEnd,inorderStart,inorderPos-1);
		// +1 在left已经用过了
    	root.right = doBuild(preorder,inorder,preorderIndex,preorderEnd,inorderPos+1,inorderEnd);
		return root;
	}
}
//leetcode submit region end(Prohibit modification and deletion)

}
