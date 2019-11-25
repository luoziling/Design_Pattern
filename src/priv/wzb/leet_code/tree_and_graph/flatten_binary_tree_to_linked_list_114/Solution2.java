package priv.wzb.leet_code.tree_and_graph.flatten_binary_tree_to_linked_list_114;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/23 21:22
 * @description:
 */
public class Solution2 {
    List<TreeNode> list = new LinkedList<>();
    public void flatten(TreeNode root) {
        TreeNode last = null;
        preOrder(root,last);

    }

    public void preOrder(TreeNode root,TreeNode last){
        // 当前节点为空则直接返回
        if (root == null){
            return;
        }
        // 如果当前节点是叶子节点也直接返回
        if (root.left==null&&root.right == null){
            last = root;
            return;
        }
        // 备份左右指针，因为过会会改变节点左右指针防止左右子树不可达
        TreeNode left = root.left;
        TreeNode right = root.right;
        // 左右子树最后一个节点
        TreeNode leftLast = null;
        TreeNode rightLast = null;
        // 前序遍历
        // 开始遍历
        // 左指针存在
        if (left!=null){
            preOrder(left,leftLast);
            // 转链表
            // 左指针置空
            root.left = null;
            // 右指针指向左子树
            root.right = left;
            last = leftLast;
        }
        if (right!=null){
            // 遍历右子树
            preOrder(right,rightLast);
            if (leftLast!=null){
                // 如果左边最后一个节点找到了
                // 将这个节点的右子树指向当前节点右子树
                leftLast.right = right;
            }
            // 记录叶子节点
            last = rightLast;
        }
    }
}
