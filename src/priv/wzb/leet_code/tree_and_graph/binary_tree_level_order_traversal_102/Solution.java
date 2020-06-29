package priv.wzb.leet_code.tree_and_graph.binary_tree_level_order_traversal_102;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

/**
 * @author Satsuki
 * @time 2020/5/13 1:01
 * @description:
 */
public class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
//        CountDownLatch latch = new CountDownLatch(1);
//        latch.countDown();
//        latch.await();

        List<List<Integer>> ans = new ArrayList<>();
        // 层次数组
        List<Integer> level = new ArrayList<>();
        // 辅助遍历的队列
        LinkedList<TreeNode> help = new LinkedList<>();
        // 分节符，用于区分层次结构
        TreeNode dummyNode = new TreeNode(Integer.MIN_VALUE);
        help.addLast(root);
        // 如果为空则直接返回
        if (root == null){
            return ans;
        }
        // 根节点直接推入分界符
        help.addLast(dummyNode);
        // 当辅助队列不为空
        while (help.size()>0){
            // 从队列中取出头节点
             TreeNode node = help.getFirst();
             help.removeFirst();
            // 如果当前节点是分界符
             if (node == dummyNode){
                 // 说明这一层遍历完毕，将数组加入结果
                 ans.add(level);
                 // 创建新数组
                 level = new ArrayList<>();
                 // 此时下一层所有节点应该都进入了队列
                 // 当队列非空插入分界符
                 if (!help.isEmpty()){
                     help.addLast(dummyNode);
                 }
             }else {
                 // 未到分界符就不断加入数
                 level.add(node.val);
                 // 节点左右不为空则入队
                 if (node.left!=null){
                     help.addLast(node.left);
                 }
                 if (node.right!=null){
                     help.addLast(node.right);
                 }
             }

        }
        return ans;
    }
}
