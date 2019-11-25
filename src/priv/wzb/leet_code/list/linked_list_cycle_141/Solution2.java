package priv.wzb.leet_code.list.linked_list_cycle_141;

/**
 * @author Satsuki
 * @time 2019/11/13 18:42
 * @description:
 * 利用跑道快慢指针
 * 如果有环那么跑的快的一定会再次追上跑得慢的
 * 追上了就说明有环
 */
public class Solution2 {
    public boolean hasCycle(ListNode head) {
        if (head == null||head.next == null){return false;}
        ListNode slow = head;
        ListNode fast = head;
        while (fast!=null&&fast.next!=null){
            // 慢的走一步
            slow = slow.next;
            // 快的走两步
            fast = fast.next.next;

            if (slow == fast){
                return true;
            }

        }

        return false;
    }
}


