package priv.wzb.leet_code.list.partition_list_86;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/13 19:48
 * @description:
 * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
 *
 * 你应当保留两个分区中每个节点的初始相对位置。
 *
 * 示例:
 *
 * 输入: head = 1->4->3->2->5->2, x = 3
 * 输出: 1->2->2->4->3->5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/partition-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 利用双链表保存数据，最后将较小链表的末尾指向较大链表的头
 * 双链表+临时节点来解决
 * 放入的时候使用辅助指针以免头节点移动
 * 小于x的放入较小链表
 * 大于等于x的放入较大链表
 * 较大链表末尾置空
 * 相连
 * 返回
 */
public class Solution {
    public ListNode partition(ListNode head, int x) {
        // 临时头节点
        ListNode smallHead = new ListNode(0);
        ListNode largeHead = new ListNode(0);
        // 辅助指针
        ListNode sptr = smallHead;
        ListNode lptr = largeHead;
        // 遍历head
        while (head!=null){
            // 比x小或者相等放入small否则放入large
            if (head.val<x){
                sptr.next = head;
                sptr = sptr.next;
//                small.add(head);
            }else{
                lptr.next = head;
                lptr = lptr.next;
//                large.add(head);
            }
            // 后移遍历
            head = head.next;
        }
        // 因为用head赋值，为了避免出错将较大一侧的数组最后一个置空
        // 然后连接使得较小一侧也不会出现问题
        lptr.next = null;
        // 相连
        sptr.next = largeHead.next;
        // 返回
        return smallHead.next;


    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(2);
        ListNode l5 = new ListNode(5);
        ListNode l6 = new ListNode(2);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        new Solution().partition(l1,3);
    }
}
   class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
