package priv.wzb.leet_code.list.middle_of_the_linked_list_876;

/**
 * @author Satsuki
 * @time 2020/3/23 18:35
 * @description:
 */
public class Solution1 {
    public ListNode middleNode(ListNode head) {
        // 使用快慢指针
        // 慢的每次走一步，快的走两步，当快指针走到末尾则满指针走到中间
        ListNode slow=head,fast=head;
        while (fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
