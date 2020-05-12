package priv.wzb.leet_code.tree_and_graph.validate_binary_search_tree_98;

/**
 * @author Satsuki
 * @time 2020/5/5 17:16
 * @description:
 */
public class Solution {
    int nowValue,preValue;

    public boolean isValidBST(TreeNode root) {
        // 中序遍历
        if (root!=null){
            isValidBST(root.left);
        }


//        boolean flag = true;
        preValue = nowValue;
        if (root!=null){
            nowValue = root.val;
        }


        // preValue 记录前面的最大记录
        if (nowValue > preValue){
            preValue = nowValue;
        }
        if (nowValue<preValue){
            return false;
        }


        if (root!=null){
            isValidBST(root.right);
        }


        return true;
    }
}
