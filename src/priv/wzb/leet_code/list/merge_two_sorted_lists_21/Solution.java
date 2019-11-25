package priv.wzb.leet_code.list.merge_two_sorted_lists_21;

import priv.wzb.interview.test.P;

/**
 * @author Satsuki
 * @time 2019/11/13 22:11
 * @description:
 */
public class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 头节点
        ListNode node = new ListNode(0);
        // 临时指针放丢失头节点
        ListNode ptr = node;
//        while (l1!=null|| l2!=null){
//            if(l1!=null&&l2!=null){
//                if (l1.val<=l2.val){
//                    node.next = l1;
//                    l1 = l1.next;
//                }
//            }else if (l2!=null){
//                node.next = l2;
//                l2 = l2.next;
//            }
//
//            if (l1!=null&&l2!=null){
//                if (l1.val>l2.val){
//                    node.next = l2;
//                    l2 = l2.next;
//                }
//            }else if (l1!=null){
//                node.next = l1;
//                l1 = l1.next;
//            }
//
//
//        }


        while (l1!=null|| l2!=null){
            if(l1!=null&&l2!=null){
                if (l1.val<=l2.val){
                    node.next = l1;
                    l1 = l1.next;
                }else {
                    node.next = l2;
                    l2 = l2.next;
                }
            }else if (l2!=null){
                node.next = l2;
                l2 = l2.next;
            }else if (l1!=null){
                node.next = l1;
                l1 = l1.next;
            }
            node = node.next;


        }

//        while (l1 != null && l2 != null) {
//            if (l1.val <= l2.val) {
//                node.next = l1;
//                l1 = l1.next;
//            } else {
//                node.next = l2;
//                l2 = l2.next;
//            }
//            node = node.next;
//        }
//        node.next = l1 == null? l2:l1;

        return ptr.next;
    }
}

   class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
