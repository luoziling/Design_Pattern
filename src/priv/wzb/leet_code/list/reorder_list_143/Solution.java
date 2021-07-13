package priv.wzb.leet_code.list.reorder_list_143;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-10-20 20:11
 * @description: 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
 * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
 * <p>
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 * 示例 1:
 * <p>
 * 给定链表 1->2->3->4, 重新排列为 1->4->2->3.
 * 示例 2:
 * <p>
 * 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reorder-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 **/

class ListNode {
	int val;
	ListNode next;

	ListNode() {
	}

	ListNode(int val) {
		this.val = val;
	}

	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}
}

public class Solution {
	public void reorderList(ListNode head) {
		// 线性表
		// 存入线性表然后照规律排序
		if (head == null) {
			return;
		}
		List<ListNode> list = new ArrayList<>();
		ListNode node = head;
		while (node != null) {
			// 遍历放入线性表
			list.add(node);
			node = node.next;
		}
		// 指向链表头尾
		int i = 0, j = list.size() - 1;
		// 遍历重新组合
		while (i < j) {
			list.get(i).next = list.get(j);
			// i必须先后移再判断
			i++;
			if (i == j) {
				break;
			}
			// 链路的打断必须是左右双向的否则容易造成奇怪的循环
			list.get(j).next = list.get(i);
			j--;
		}
		// 循环后i指向最后一个，那么下一个置null
		list.get(i).next = null;
	}
}
