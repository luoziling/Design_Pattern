package priv.wzb.leet_code.list.linked_list_cycle_141.llc142;

/**
 * @author Satsuki
 * @time 2019/11/13 19:10
 * @description:
 * 利用快慢指针
 * 如果相遇，此时通过数学公式分析可知
 * 从相遇点触发与从起点触发的两个指针一起向前走，如果相遇则到达了环形头
 */
public class Solution1 {

    private ListNode getIntersect(ListNode head) {
        ListNode tortoise = head;
        ListNode hare = head;

        // A fast pointer will either loop around a cycle and meet the slow
        // pointer or reach the `null` at the end of a non-cyclic list.
        while (hare != null && hare.next != null) {
            tortoise = tortoise.next;
            hare = hare.next.next;
            if (tortoise == hare) {
                return tortoise;
            }
        }

        return null;
    }
    public ListNode detectCycle(ListNode head) {
        // 只有一个节点或者没有节点无法成环直接返回
        if (head == null || head.next == null){
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        ListNode ptr = null;

        // 先确定成环
        while (fast != null && fast.next!= null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                ptr = slow;
                break;
            }
        }
        if (ptr == null){
            return null;
        }

        // 慢节点走的路程是快节点的二分之一
        // 利用公式可得若快慢节点相遇则从头节点开始一步一步走
        // 与慢节点从相遇节点开始一步一步走走到相遇即为环的初始
        while (head!=ptr){
            head = head.next;
            ptr = ptr.next;
        }

        return head;
    }
}
