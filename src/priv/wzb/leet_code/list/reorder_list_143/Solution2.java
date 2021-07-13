package priv.wzb.leet_code.list.reorder_list_143;

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


public class Solution2 {
	public void reorderList(ListNode head) {
		// 寻找中点、反转链表、合并
		if (head == null) {
			return;
		}
		ListNode mid = middleNode(head);
		// 设定两个链表
		// 反转第二个
		ListNode l1 = head;
		ListNode l2 = mid.next;
		mid.next = null;
		l2 = reverseList(l2);
		// 合并
		mergeList(l1, l2);
	}

	/**
	 * 快慢指针，由于快指针是慢指针的两倍速，所以快指针到中点，慢指针就到一半
	 *
	 * @param head
	 * @return
	 */
	public ListNode middleNode(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	public ListNode reverseList(ListNode head) {
		ListNode prev = null;
		ListNode curr = head;
		while (curr != null) {
			ListNode nextTemp = curr.next;
			curr.next = prev;
			prev = curr;
			curr = nextTemp;
		}
		return prev;
	}

	/**
	 * 合并链表
	 *
	 * @param l1
	 * @param l2
	 */
	public void mergeList(ListNode l1, ListNode l2) {
		// 记录更改前的下一个节点
		ListNode l1_tmp;
		ListNode l2_tmp;
		while (l1 != null && l2 != null) {
			l1_tmp = l1.next;
			l2_tmp = l2.next;

			l1.next = l2;
			l1 = l1_tmp;
			l2.next = l1;
			l2 = l2_tmp;
		}
	}
}
