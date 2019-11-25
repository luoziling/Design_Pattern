package priv.wzb.leet_code.list.reverse_linked_list_206;

/**
 * @author Satsuki
 * @time 2019/11/10 22:00
 * @description:
 * 不带头结点的单链表
 */
public class Solution1 {
    public ListNode reverseList(ListNode head) {
        // 反转后的链表
        // 因为在逆转的过程中倒序的链表的头节点位置没变
        // 其他原先链表的节点在不停的插入头节点
        ListNode newHead = null;
        // 遍历原先节点一次插入到新节点就完成了倒置
        while (head!=null){
            // 备份下一个节点
            ListNode next = head.next;
            // 将当前节点的next指针指向新节点
            head.next = newHead;
            // 将新的链表换个顺序，将新链表的头节点指向第一个节点
            newHead = head;
            head = next;
        }
        return newHead;
    }
}
