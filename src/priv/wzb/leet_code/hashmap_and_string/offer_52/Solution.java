package priv.wzb.leet_code.hashmap_and_string.offer_52;

import java.util.ArrayList;
import java.util.List;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/8/15 23:08
 * @description:
 * @since 1.0.0
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 思路：该题转为A中节点是否在B中出现，如果出现代表存在交点
        List<ListNode> aList = new ArrayList<>();
        while (headA != null){
            aList.add(headA);
            headA = headA.next;
        }
        while (headB!=null){
            if (aList.contains(headB)){
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }
}

   class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
  }
