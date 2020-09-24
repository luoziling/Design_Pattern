package priv.wzb.leet_code.tree_and_graph.merge_two_binart_trees_617;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 20:01
 * @description: 深度优先遍历解法
 **/

public class Solution2 {
	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null){
			return t2;
		}
		if (t2 == null){
			return t1;
		}
		TreeNode merge = new TreeNode(t1.val + t2.val);
		merge.left = mergeTrees(t1.left,t2.left);
		merge.right = mergeTrees(t1.right,t2.right);
		return merge;
	}
}
