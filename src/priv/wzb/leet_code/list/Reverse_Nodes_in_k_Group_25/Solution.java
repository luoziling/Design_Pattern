package priv.wzb.leet_code.list.Reverse_Nodes_in_k_Group_25;

import java.util.List;

/**
 * @author Satsuki
 * @time 2020/5/16 1:44
 * @description:
 */
public class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        // 空想的pre节点（接在头节点之前
        ListNode pre = new ListNode(Integer.MIN_VALUE);
        pre.next = head;
        // 用于记录头节点返回
        ListNode hair = pre;

        // 初始化tail，代表每个小段链表的末尾
        ListNode tail = pre;
        ListNode start,next;

        // 遍历链表
        while (tail.next!=null){
            // 移动tail到小段链表末尾
            for(int i=0;i<k&&tail!=null;i++){
                tail = tail.next;
            }
            if (tail == null){
                break;
            }
            // 设置next
            next = tail.next;
            // 设置start为起始
            start = pre.next;
            // 断开链表
            tail.next = null;
            // 反转链表（head，tail
            pre.next = reverse(start);
            // 接上链表末尾,此时start为反转后链表末尾
            start.next = next;
            // 移动指针寻找下一段链表
            pre = start;// 此时start已经是当前逆序链表的末尾
            tail = pre; // 移动末尾为pre下一次继续根据k移动tail


        }

        return hair.next;
    }

    /**
     * 反转链表
     * @param head 头节点
     * @return
     */
    private ListNode reverse(ListNode head){
        // pre是指向前一个节点的指针，初始头节点前面的null
        ListNode pre = null;
        // curr是当前节点
        ListNode curr = head;
        while (curr!=null){
            // 记录下一个节点的指针
            ListNode next = curr.next;
            // 将指向下一个节点的指针反转指向前一个节点
            curr.next = pre;
            // 更新前一个节点（后移
            pre = curr;
            // 后移当前节点
            curr = next;
        }
        // 最后pre会移动到最后，但此时由于链表反转pre正好是反转后的头
        return pre;
    }
}

