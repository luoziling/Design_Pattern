package priv.wzb.leet_code.list.linked_list_cycle_141;

import java.util.HashSet;

/**
 * @author Satsuki
 * @time 2019/11/13 18:42
 * @description:
 * 利用set与contains方法
 * 遍历链表将节点不断加入set如果遇到一个加入过的节点就说明由环
 */
public class Solution1 {
    public boolean hasCycle(ListNode head) {
        if (head == null||head.next == null){return false;}
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast){
            if ( fast == null||fast.next == null){
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        return true;
    }
}


