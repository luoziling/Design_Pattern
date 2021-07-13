package priv.wzb.leet_code.tree_and_graph.offer_33;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-06-15 20:41
 * @description: BST后序遍历是否符合规范
 * 思路由于BST的有序特性+后序遍历的性质，模拟着遍历，若中间出错则无法构建BST
 **/

public class Solution {
	public boolean verifyPostorder(int[] postorder) {
		return dfs(postorder,0,postorder.length);
	}

	/**
	 * 深度遍历
	 * @param postorder 遍历结果
	 * @param start 初始节点
	 * @param end 末级节点
	 * @return
	 */
	private boolean dfs(int[] postorder, int start, int end) {
		// 若遍历完毕未出错，返回true
		if (start>=end-1){
			return true;
		}
		// 找到根节点，由于后续遍历，根节点永远是倒序第一个
		// 由于BST性质根节点是最中间值，因此进行左右划分，在划分过程中不是有序就出问题 返回false
		int root = postorder[end - 1];
		// 左子树都比根节点小，右子树都比根节点大，若出现比根节点大的说明到了分界点
		int i = start;
		while (postorder[i] < root){
			i++;
		}
		// 从分界点开始遍历，若递增中出现递减则说明BST构造失败数据有问题
		for (int j = i; j < end; j++) {
			if (postorder[j]<root){
				return false;
			}
		}
		// 递归遍历左右子树，确保左右子树都符合BST规范
		// 确保每个元素都符合规范
		return dfs(postorder,start,i) && dfs(postorder,i,end-1);

	}
}
