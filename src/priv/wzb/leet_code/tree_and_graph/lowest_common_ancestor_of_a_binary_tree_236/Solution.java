package priv.wzb.leet_code.tree_and_graph.lowest_common_ancestor_of_a_binary_tree_236;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/21 22:13
 * @description:
 */
public class Solution {
    List<TreeNode> path = new LinkedList<>();
    int finish = 0;


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> resP = new LinkedList<>();
        List<TreeNode> resQ = new LinkedList<>();


        preOrder(root,p,resP);
        path.clear();
        finish = 0;
        preOrder(root,q,resQ);
        int pathLen = 0;
        if (resP.size()< resQ.size()){
            pathLen = resP.size();
        }else {
            pathLen = resQ.size();
        }

        TreeNode res = null;
        for (int i = 0; i < pathLen; i++) {
//            if (resQ.get(i)== resP.get(i)){
            if (resQ.get(i).equals(resP.get(i))){
                res = resP.get(i);
            }
        }
        return res;
    }

    /**
     * 求路径
     * @param node
     * @param search
     */
    void preOrder(TreeNode node,TreeNode search,List<TreeNode> res){
        // 当节点为空或者找到了就返回
        if (node == null||finish==1){
            return;
        }
        path.add(node);
        if (node == search){
            res = new LinkedList<>(path);
        }
        preOrder(node.left,search,res);
        preOrder(node.right,search,res);
        path.remove(path.size()-1);
    }
}

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
     TreeNode(int x) { val = x; }
  }
