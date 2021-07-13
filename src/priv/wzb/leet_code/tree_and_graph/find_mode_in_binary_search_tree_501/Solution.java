package priv.wzb.leet_code.tree_and_graph.find_mode_in_binary_search_tree_501;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 19:37
 * @description:
 * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
 *
 * 假定 BST 有如下定义：
 *
 * 结点左子树中所含结点的值小于等于当前结点的值
 * 结点右子树中所含结点的值大于等于当前结点的值
 * 左子树和右子树都是二叉搜索树
 * 例如：
 * 给定 BST [1,null,2,2],
 *
 *    1
 *     \
 *      2
 *     /
 *    2
 * 返回[2].
 *
 **/
class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode(int x) { val = x; }
}
public class Solution {
	private Map<Integer,Integer> map = new HashMap<>(4);
	public int[] findMode(TreeNode root) {
		AtomicInteger max = new AtomicInteger(-1);
		ArrayList<Integer> intArray = new ArrayList<>();
		traverse(root);
		map.forEach((k,v) ->{
			if (v> max.get()){
				max.set(v);
			}
		});
		map.forEach((k,v) ->{
			if (v== max.get()){
				intArray.add(k);
			}
		});
		int[] res = new int[intArray.size()];
		AtomicInteger i = new AtomicInteger();
		intArray.stream().forEach(a->{
			res[i.getAndIncrement()] = a;
		});
		return res;
	}
	private void traverse(TreeNode root){
		if (root == null){
			return;
		}
		map.put(root.val,map.get(root.val)==null?0:map.get(root.val) + 1);
		traverse(root.left);
		traverse(root.right);
	}

}
