package priv.wzb.leet_code.tree_and_graph.path_sum_112;

/**
 * @author Satsuki
 * @time 2019/11/21 21:41
 * @description:
 * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例: 
 * 给定如下二叉树，以及目标和 sum = 22，
 *
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \      \
 *         7    2      1
 * 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    boolean flag;
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        flag = false;

        preFix(root,sum,0);
        return flag;
    }
    public void preFix(TreeNode root, int sum,int pathSum){
        if (root == null){
            return;
        }

        // 遍历当前节点
        pathSum += root.val;
        // 要到达根击点才能判定
        if (root.left == null&&root.right== null){
            if (pathSum == sum){
                flag = true;
            }
        }
        preFix(root.left,sum,pathSum);
        preFix(root.right,sum,pathSum);
        pathSum-=root.val;
    }
}

class TreeNode {
     int val;
     TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
