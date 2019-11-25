package priv.wzb.leet_code.tree_and_graph.flatten_binary_tree_to_linked_list_114;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/23 21:22
 * @description:
 * 可以发现展开的顺序其实就是二叉树的先序遍历。算法和 94 题中序遍历的 Morris 算法有些神似，我们需要两步完成这道题。
 *
 * 将左子树插入到右子树的地方
 * 将原来的右子树接到左子树的最右边节点
 * 考虑新的右子树的根节点，一直重复上边的过程，直到新的右子树为 null
 *
 * 作者：windliang
 * 链接：https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by--26/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * 找左子树最右节点
 * 将根节点右子树放到根节点左子树最右节点的右边
 * 将根节点的左子树变为右子树
 * 将根节点左子树置空，循环
 */
public class Solution3 {
    public void flatten(TreeNode root) {
        while (root!=null){
            // 左子树为null，直接考虑下一个节点
            if (root.left== null){
                root = root.right;
            }else {
                // 找左子树最右边节点
                TreeNode pre = root.left;
                while (pre.right!=null){
                    pre = pre.right;
                }
                // 将原来右子树接到左子树的最右边节点
                pre.right = root.right;
                // 将左子树插入到右子树的地方
                root.right = root.left;
                // 置空
                root.left = null;
                // 考虑下一个节点
                root = root.right;
            }
        }


    }
}
