package priv.wzb.leet_code.tree_and_graph.offer_36;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/6/21 21:25
 * @description:
 * @since 1.0.0
 */
public class Solution {
    // 双节点分别指向上一个节点和头节点
    // 也可以说是尾节点和头节点，pre会随着遍历到末尾，头节点初始化后就不变动
    Node pre,head;
    public Node treeToDoublyList(Node root) {
        if (root == null){
            return null;
        }
        // 二叉搜索树转循环双向链表
        // 思路：借助二叉搜索树中顺遍历有序的特性顺序构建链表
        dfs(root);
        // 双向构建完毕构建循环
        head.left = pre;
        pre.right = head;
        return head;
    }

    /**
     * 中序遍历->有序遍历 构造双向循环链表
     * @param root BST根节点
     */
    private void dfs(Node root) {
        if (root == null){
            // 遍历为空返回
            return;
        }
        dfs(root.left);
        // 中序遍历
//        Node nowNode = new Node(root.val);
        // 初始化，可以不构建新的节点，直接拆分原有结构进行转化
        if (pre != null){
            // 构建双向链表
            pre.right = root;
            root.left = pre;
        }else {
            // 头节点只初始化一次
            head = root;
        }

        // 当前节点是下次遍历的上一个有序节点
        // pre不断遍历直到最后指向最后一个节点
        pre = root;

        dfs(root.right);
    }
}

class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val,Node _left,Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};
