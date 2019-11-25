package priv.wzb.leet_code.tree_and_graph.binary_tree_right_side_view_199;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/23 22:25
 * @description:
 * 就像深度优先搜索可以保证我们最先访问某个深度的最右结点那样，广度优先搜索可以保证我们 最后 访问它。
 */
public class Solution2 {
    public List<Integer> rightSideView(TreeNode root) {
        // 记录深度与值
        Map<Integer,Integer> rightmostValueAtDepth = new HashMap<>();
        // 树的深度
        int max_depth = -1;

        // 从深度优先的栈改为双向队列
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> depthQueue = new LinkedList<>();
        // 根节点入队
        nodeQueue.add(root);
        depthQueue.add(0);

        // 遍历
        // 当节点队列不为空
        while (!nodeQueue.isEmpty()){
            // remove就是retrieves and removes the head of this queue,检索并删除此队列的头
            TreeNode node = nodeQueue.remove();
            int depth = depthQueue.remove();

            if (node!=null){
                max_depth = Math.max(depth,max_depth);

                // 不停的添加到map中即可，因为map的key 是唯一的所以多次添加会造成的也是更新罢了
                rightmostValueAtDepth.put(depth,node.val);

                // 入队
                // 从最至右遍历即可
                // 在遍历过程中会不停更新最右侧的值
                nodeQueue.add(node.left);
                nodeQueue.add(node.right);

                depthQueue.add(depth+1);
                depthQueue.add(depth+1);
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
