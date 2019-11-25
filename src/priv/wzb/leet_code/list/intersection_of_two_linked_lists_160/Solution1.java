package priv.wzb.leet_code.list.intersection_of_two_linked_lists_160;

/**
 * @author Satsuki
 * @time 2019/11/10 22:43
 * @description:
 */
public class Solution1 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 若长度不等就将链表移动到定长再开始比较
        int len1 = 0;
        int len2 = 0;
        ListNode helpA = headA;
        ListNode helpB = headB;
        while (helpA!=null){
            len1++;
            helpA = helpA.next;
        }
        while (helpB!=null){
            len2++;
            helpB = helpB.next;
        }
        int step = 0;
        if (len1>len2){
            // A长，A向后移
            step = len1-len2;
            for (int i = 0; i < step; i++) {
                headA = headA.next;
            }
        }else if(len1<len2){
            step = len2-len1;
            // B长，B向后移
            for (int i = 0; i < step; i++) {
                headB = headB.next;
            }
        }
        // 一样长
        while (headA!=null&&headB!=null){
            if (headA == headB){
                // 找到相同的就返回
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }

        return null;

    }
}
