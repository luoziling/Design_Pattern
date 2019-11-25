package priv.wzb.leet_code.tree_and_graph.lowest_common_ancestor_of_a_binary_tree_236;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/21 22:31
 * @description:
如果每个节点都有父指针，那么我们可以从 p 和 q 返回以获取它们的祖先。在这个遍历过程中，我们得到的第一个公共节点是 LCA 节点。我们可以在遍历树时将父指针保存在字典中。

算法：

从根节点开始遍历树。
在找到 p 和 q 之前，将父指针存储在字典中。
一旦我们找到了 p 和 q，我们就可以使用父亲字典获得 p 的所有祖先，并添加到一个称为祖先的集合中。
同样，我们遍历节点 q 的祖先。如果祖先存在于为 p 设置的祖先中，这意味着这是 p 和 q 之间的第一个共同祖先（同时向上遍历），因此这是 LCA 节点。

作者：LeetCode
链接：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/solution/er-cha-shu-de-zui-jin-gong-gong-zu-xian-by-leetcod/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class Solution2 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 用于树遍历的栈(这是一个双端队列
        Deque<TreeNode> stack = new ArrayDeque<>();

        // 使用hashmap表示父指针
        Map<TreeNode,TreeNode> parent = new HashMap<>();

        parent.put(root,null);
        stack.push(root);

        // 迭代直至找到p与q
        while (!parent.containsKey(p) || !parent.containsKey(q)){
            // 出栈顶
            TreeNode node = stack.pop();
            // 当遍历树，保存父指针
            if (node.left!=null){
                parent.put(node.left,node);
                stack.push(node.left);
            }
            if (node.right!=null){
                parent.put(node.right,node);
                stack.push(node.right);
            }
        }
        Set<TreeNode> ancestors = new HashSet<>();

        // 使用父指针处理所有p的祖先
        while (p!=null){
            ancestors.add(p);
            p = parent.get(p);
        }
        // 在p的祖先节点中第一个包含q的节点就是pq的公共祖先
        while (!ancestors.contains(q)){
            q = parent.get(q);
        }
        return q;
    }
}
