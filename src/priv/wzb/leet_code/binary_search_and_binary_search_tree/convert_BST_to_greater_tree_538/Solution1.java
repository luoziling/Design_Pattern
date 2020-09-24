package priv.wzb.leet_code.binary_search_and_binary_search_tree.convert_BST_to_greater_tree_538;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-21 19:34
 * @description:
 **/

public class Solution1 {
	/**
	 * 逆序中序遍历中不断记录较大值的合
	 */
	int sum = 0;
	public TreeNode convertBST(TreeNode root) {
		if (root != null){
			// 先遍历右子树从而达到逆序的效果
			convertBST(root.right);
			// 到达中序的位置，进行累加操作
			sum += root.val;
			root.val = sum;
			// 遍历左子树(这个作用主要是累加
			convertBST(root.left);
		}
		return root;
	}
}
