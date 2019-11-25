package priv.wzb.leet_code.tree_and_graph.lowest_common_ancestor_of_a_binary_tree_236;

/**
 * @author Satsuki
 * @time 2019/11/21 22:31
 * @description:
 * 方法一：递归
 * 这种方法非常直观。先深度遍历改树。当你遇到节点 p 或 q 时，返回一些布尔标记。该标志有助于确定是否在任何路径中找到了所需的节点。最不常见的祖先将是两个子树递归都返回真标志的节点。它也可以是一个节点，它本身是p或q中的一个，对于这个节点,子树递归返回一个真标志。
 *
 * 让我们看看基于这个想法的形式算法。
 *
 * 算法：
 *
 * 从根节点开始遍历树。
 * 如果当前节点本身是 p 或 q 中的一个，我们会将变量 mid 标记为 true，并继续搜索左右分支中的另一个节点。
 * 如果左分支或右分支中的任何一个返回 true，则表示在下面找到了两个节点中的一个。
 * 如果在遍历的任何点上，左、右或中三个标志中的任意两个变为 true，这意味着我们找到了节点 p 和 q 的最近公共祖先。
 * 让我们看一个示例，然后搜索树中两个节点 9 和 11 的最近公共祖先。
 *
 * 作者：LeetCode
 * 链接：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/solution/er-cha-shu-de-zui-jin-gong-gong-zu-xian-by-leetcod/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class Solution1 {
    private TreeNode ans = null;

    private boolean recurseTree(TreeNode currentNode,TreeNode p,TreeNode q){
        // 如果搜索到了分支的结尾那么返回false
        if (currentNode == null){
            return false;
        }
        // 递归左边
        // left代表如果在左子树找到了返回1否则返回0
        int left = this.recurseTree(currentNode.left,p,q)?1:0;

        // 递归右边
        int right = this.recurseTree(currentNode.right,p,q)?1:0;

        // 如果当前节点是p或者q其中一个
        int mid = (currentNode==p||currentNode==q)?1:0;


        // 如果满足任意两个条件
        if (mid+left+right>=2){
            this.ans = currentNode;
        }

        // 如果一个条件为真则返回true
        return (mid+left+right>0);
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        this.recurseTree(root,p,q);
        return this.ans;
    }
}
