package priv.wzb.leet_code.tree_and_graph.path_sum_112;

/**
 * @author Satsuki
 * @time 2019/11/21 21:54
 * @description:
 */
public class Solution1 {
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null)
            return false;

        sum-= root.val;
        // 到达根节点判定
        if (root.left== null && root.right == null){
            return (sum==0);
        }

        return hasPathSum(root.left,sum)||hasPathSum(root.right,sum);
    }
}
