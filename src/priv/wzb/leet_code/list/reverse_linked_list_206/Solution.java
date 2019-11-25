package priv.wzb.leet_code.list.reverse_linked_list_206;

/**
 * @author Satsuki
 * @time 2019/11/10 21:33
 * @description:
 *
 */



/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 * 不带头节点的反转链表
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        // 反转后的链表
        // 因为在逆转的过程中倒序的链表的头节点位置没变
        // 其他原先链表的节点在不停的插入头节点
        ListNode newHead = null;
        while (head!=null){
            // 备份head.next
            ListNode next = head.next;
            // 更新head.next
            head.next = newHead;
            // 移动newHead
            newHead = head;
            // 后移遍历链表
            head = next;
        }
        return newHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
    }
}

class ListNode{
    int val;
    ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }
}
