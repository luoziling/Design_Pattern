package priv.wzb.leet_code.tree_and_graph.path_sum_two_113;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/21 21:14
 * @description:
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 * 给定如下二叉树，以及目标和 sum = 22，
 *
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \    / \
 *         7    2  5   1
 * 返回:
 *
 * [
 *    [5,4,11,2],
 *    [5,8,4,5]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 思路
 * 深度遍历
 * 在遍历时有一个sum来累计和并且判定是否到叶子节点到叶子节点就进行判定是否链表合格
 * 合格加入res
 * 前序中序和后序其实就是对树中某个节点的遍历前，遍历中遍历后采取的操作
 */
public class Solution {
    List<List<Integer>> res = new LinkedList<>();
    LinkedList<Integer> temp = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        preFix(root,sum,0);
        return res;
    }

    /**
     * 找寻符合条件的路径
     * @param root 根节点
     * @param sum 目标和
     * @param pathSum 零时和
     */
    public void preFix(TreeNode root,int sum,int pathSum){
        // 判空则返回
        if (root == null){
            return;
        }

        pathSum+=root.val;
        temp.add(root.val);

        // 到了根节点
        if (root.left == null && root.right == null){
            // 达到目标和
            if (pathSum == sum){
                res.add(new LinkedList<>(temp));
            }

//            return;
        }
        // 遍历

        preFix(root.left,sum,pathSum);
        preFix(root.right,sum,pathSum);
        // 清除
        pathSum-=root.val;
        temp.removeLast();
    }
}

  class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
