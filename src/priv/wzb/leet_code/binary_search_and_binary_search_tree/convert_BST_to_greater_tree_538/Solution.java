package priv.wzb.leet_code.binary_search_and_binary_search_tree.convert_BST_to_greater_tree_538;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-21 19:27
 * @description:
 **/

public class Solution {
	// 记录根节点
	private TreeNode rootNode;
	public TreeNode convertBST(TreeNode root) {
//		if (root == )
		rootNode = root;
		// 递归遍历二叉树
		// 前序/中序/后序/层次？
		// 后续
		convertBST(root.left);
		convertBST(root.right);
		if (root.right.val>root.val){
			root.val += root.right.val;
		}
		return null;

	}
}


class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;
  TreeNode(int x) { val = x; }
 }
