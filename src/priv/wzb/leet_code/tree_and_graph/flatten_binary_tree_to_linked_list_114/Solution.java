package priv.wzb.leet_code.tree_and_graph.flatten_binary_tree_to_linked_list_114;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/23 21:14
 * @description:
 */
public class Solution {
    List<TreeNode> list = new LinkedList<>();
    public void flatten(TreeNode root) {
        preOrder(root);
        TreeNode nowNode = null;
        for (int i = 0; i < list.size(); i++) {
            nowNode = list.get(i);
            nowNode.left = null;
            if (i<list.size()-1){
                nowNode.right = list.get(i+1);
            }else {
                nowNode.right = null;
            }

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

class TreeNode {
     int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
