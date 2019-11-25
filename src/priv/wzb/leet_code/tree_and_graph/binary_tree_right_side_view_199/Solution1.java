package priv.wzb.leet_code.tree_and_graph.binary_tree_right_side_view_199;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/23 22:25
 * @description:
 * // 利用深度优先搜索，但是总是先访问右子树
 * 深度优先
 * 根右左
 * NB
 */
public class Solution1 {
    public List<Integer> rightSideView(TreeNode root) {
        // 记录深度与值
        Map<Integer,Integer> rightmostValueAtDepth = new HashMap<>();
        // 树的深度
        int max_depth = -1;

        // 使用两个同步的栈，提供一个每个堆栈上具有相同的隐含关联值
        // 用两个栈来表示当前节点和当前层数
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        nodeStack.push(root);
        depthStack.push(0);

        // 当节点栈不为空
        while (!nodeStack.isEmpty()){
            // 头节点出栈
            TreeNode node = nodeStack.pop();
            // 获取当前访问的节点深度
            int depth = depthStack.pop();
            if (node!=null){
                // 节点不为空就一直更新记录树的深度
                max_depth = Math.max(max_depth,depth);

                // 其实这里就是从右向左进行遍历的过程中遇到第一个有效数值就入map
                // map利用了两个integer类型第一个代表深度第二个代表这个深度最右侧有效的值
//                我们在特定深度遇到的第一个包含正确值的节点
                if (!rightmostValueAtDepth.containsKey(depth)){
                    rightmostValueAtDepth.put(depth,node.val);
                }
                // 这里十分巧妙，原来按照原本的深度优先搜索的思想
                // 那么每次都需要访问最右侧的节点
                // 如果是普通队列，先进先出那么就需要先入队右侧节点再入队左侧节点
                // 但是这里采用了堆栈，先进后出，所以在后续出栈的时候，是按照最后入栈的顺序进行出栈的
                // 访问左右
                nodeStack.push(node.left);
                nodeStack.push(node.right);
                // 同步深度
                depthStack.push(depth+1);
                depthStack.push(depth+1);
            }
        }

        // 最后，构造结果
        List<Integer> rightView = new ArrayList<>();
        for(int depth = 0;depth<=max_depth;depth++){
            rightView.add(rightmostValueAtDepth.get(depth));
        }
        return rightView;
    }
}
