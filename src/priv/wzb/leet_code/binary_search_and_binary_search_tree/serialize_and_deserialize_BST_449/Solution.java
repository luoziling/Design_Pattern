package priv.wzb.leet_code.binary_search_and_binary_search_tree.serialize_and_deserialize_BST_449;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/25 22:02
 * @description:
 * 序列化是将数据结构或对象转换为一系列位的过程，以便它可以存储在文件或内存缓冲区中，或通过网络连接链路传输，以便稍后在同一个或另一个计算机环境中重建。
 *
 * 设计一个算法来序列化和反序列化二叉搜索树。 对序列化/反序列化算法的工作方式没有限制。 您只需确保二叉搜索树可以序列化为字符串，并且可以将该字符串反序列化为最初的二叉搜索树。
 *
 * 编码的字符串应尽可能紧凑。
 *
 * 注意：不要使用类成员/全局/静态变量来存储状态。 你的序列化和反序列化算法应该是无状态的。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/serialize-and-deserialize-bst
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 其实就是在考察二叉排序树的插入，遍历
 * 并且必须通过前序遍历进行序列化和反序列化
 * 如果是中序则无法还原二叉排序树会退化成一个有序链表
 * 后序遍历无法保证根节点的还原所以也不行
 */
public class Solution {
    // BST序列化后的字符串
    String data = "";
    /**
     *
     * 构建二叉排序/查找树的插入
     * @param root BST的根节点
     * @param insertNode 待插入节点
     */
    void BSTInsert(TreeNode root,TreeNode insertNode){
        if (root == null){
            return;
        }
        // 待插入节点的指小于根节点就插入到根节点左侧
        if (insertNode.val<root.val){
            // 左侧不为空就递归继续查询该插入的位置
            if (root.left!=null){
                BSTInsert(root.left,insertNode);
            }else {
                // 某个节点的左侧为空，此时也判定了待插入节点的值小于左侧节点的值
                // 插入到左侧即可
                root.left = insertNode;
            }

        }else {
            // 插入到右侧与左侧相同
            if (root.right!=null){
                BSTInsert(root,insertNode);
            }else {
                root.right = insertNode;
            }
        }
    }

    /**
     * 前序遍历BST将node.val转换为String
     * @param root
     */
    void BSTPreorder(TreeNode root){
        if (root== null){
            return;
        }
        // 数字转为String
        String strval = String.valueOf(root.val);
        data=data + strval + "#";
        BSTPreorder(root.left);
        BSTPreorder(root.right);
    }


    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        // 通过前序遍历转为字符串
        BSTPreorder(root);
        return data;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // 判空
        if (data.length() == 0){
            return null;
        }
        // 构建出节点
        List<TreeNode> list = new LinkedList<>();
        // 分割
        String[] split = data.split("#");
        for (String s : split){
            list.add(new TreeNode(Integer.valueOf(s)));
        }
        // 构建BST
        for (int i = 1; i < list.size(); i++) {
            // 从第一个开始因为第0个是根节点
            BSTInsert(list.get(0),list.get(i));
        }
        return list.get(0);
//        String[] arr = data.split("#");
//        return builder(arr,0,arr.length-1);
    }

    // 通过
    private TreeNode builder(String[] arr,int lo,int hi){
        if (lo>hi) return null;
        TreeNode root = new TreeNode(Integer.valueOf(arr[lo]));
        int index = hi+1;
        for (int i = lo+1; i <= hi; i++) {
            if (Integer.valueOf(arr[i])>root.val){
                index = i;
                break;
            }
        }
        root.left = builder(arr,lo+1,index-1);
        root.right = builder(arr,index,hi);
        return root;
    }

}
// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
