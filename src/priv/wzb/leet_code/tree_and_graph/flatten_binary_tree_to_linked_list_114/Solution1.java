package priv.wzb.leet_code.tree_and_graph.flatten_binary_tree_to_linked_list_114;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/23 21:22
 * @description:
 */
public class Solution1 {
    List<TreeNode> list = new LinkedList<>();
    public void flatten(TreeNode root) {
        preOrder(root);
        TreeNode nowNode = null;
        for (int i = 1; i < list.size(); i++) {
            nowNode = list.get(i-1);
            nowNode.left = null;
            nowNode.right = list.get(i);


        }
    }

    public void preOrder(TreeNode root){
        if (root == null){
            return;
        }
        // 前序遍历
        // 加入容器
        list.add(root);
        preOrder(root.left);
        preOrder(root.right);
    }
}
