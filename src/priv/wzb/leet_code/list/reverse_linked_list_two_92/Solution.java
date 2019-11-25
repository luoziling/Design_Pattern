package priv.wzb.leet_code.list.reverse_linked_list_two_92;

/**
 * @author Satsuki
 * @time 2019/11/10 22:05
 * @description:
 */
public class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        // 计算需要逆置的节点个数
        int changeLen = n-m+1;
        // 初始化开始逆置的节点的前驱
        ListNode preHead = null;
        // 最终转换后的链表的头节点，非特殊情况即为head
        ListNode result = head;
        // 将head前移m-1个位置
        for (int i = 0; head!=null&&i < m - 1; i++) {
            // 记录开始逆置的节点的前驱
            preHead = head;
            head = head.next;
        }
        // 将modify_list_tail指向当前的head，即逆置后的链表尾
        ListNode modifyListTail = head;
        // 逆置后的链表
        ListNode newHead = null;
        for (int i = 0; head!=null&&i < changeLen; i++) {
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        // 连接逆置后的链表尾与你之短的后一个节点
        modifyListTail.next = head;

        if (preHead!=null){
            // 当preHead不为空说明不是从第一个节点开始逆置
            // 将逆置链表开始节点的前驱与逆置后的头节点相连
            preHead.next = newHead;
        }else {
            // 如果preHead为空说明从第一个节点开始逆置
            // 结果即为逆置后的头节点
            result = newHead;
        }
        return result;
    }
}
class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }