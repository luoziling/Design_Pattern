package priv.wzb.leet_code.tree_and_graph.binary_tree_right_side_view_199;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/23 22:14
 * @description:
 * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 *
 * 示例:
 *
 * 输入: [1,2,3,null,5,null,4]
 * 输出: [1, 3, 4]
 * 解释:
 *
 *    1            <---
 *  /   \
 * 2     3         <---
 *  \     \
 *   5     4       <---
 *
 * 利用二叉树的层次遍历
 * 每次将每一层的最后一个节点加入到集合中即可
 */
public class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        // 按层次遍历最后一个节点
        List<Integer> view = new LinkedList<>();
        // 广度优先搜索队列<节点，层数>
        LinkedList<HashMap<TreeNode,Integer>> queue = new LinkedList<>();

        if (root!=null){
            // 首先将根节点入队列
//            queue.add();
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
