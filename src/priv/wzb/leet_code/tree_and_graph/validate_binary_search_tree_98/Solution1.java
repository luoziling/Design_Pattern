package priv.wzb.leet_code.tree_and_graph.validate_binary_search_tree_98;

import java.util.Stack;

/**
 * @author Satsuki
 * @time 2020/5/5 17:16
 * @description:
 */
public class Solution1 {
    int nowValue,preValue;

    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        double inorder = -Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null){
            while (root!=null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // 中序遍历得到的节点值小于前面节点inorder说明不是二叉搜索树
            if (root.val<=inorder) return false;
            inorder = root.val;
            root = root.right;
        }
        return true;
    }
}
