package priv.wzb.leet_code.array.reverse_linked_list_ii_92;

import java.util.LinkedList;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-03-18 21:12
 * @description:
 **/

public class Solution {
	public ListNode reverseBetween(ListNode head, int left, int right) {
		LinkedList<ListNode> stack = new LinkedList<>();
		ListNode cur = head;
		int now = 0;
		while (head.next != null){
			if (now >= left && now <= right){
				stack.push(head);
			}
			head = head.next;
		}
		ListNode res = new ListNode();
		ListNode resHead = res;
		while (cur.next != null){
			if (now >= left && now <= right){
				stack.push(head);
				res.next = stack.pop();
				res = res.next;
				cur = cur.next;
			}else {
				res = cur;
				cur = cur.next;
				res = res.next;
			}
		}
		return resHead;
	}
}


   class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
