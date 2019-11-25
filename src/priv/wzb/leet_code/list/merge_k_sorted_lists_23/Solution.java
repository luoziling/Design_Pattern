package priv.wzb.leet_code.list.merge_k_sorted_lists_23;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/13 22:33
 * @description:
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 *
 * 示例:
 *
 * 输入:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-k-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 暴力求解
 * 放到一起
 * 排序
 * 连接
 * 返回
 */
public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0){
            return null;
        }
        List<ListNode> nodeList = new LinkedList<>();
        // 放一起
        for(ListNode listNode : lists){
            while (listNode!=null){
                nodeList.add(listNode);
                listNode = listNode.next;
            }
        }
        // 排序
        Collections.sort(nodeList,new MyComparator());
        // 串连
        for (int i = 0; i < nodeList.size()-1; i++) {
            nodeList.get(i).next = nodeList.get(i+1);
        }
        if (nodeList.size()>0){
            ((LinkedList<ListNode>) nodeList).getLast().next = null;
            // 返回
            return nodeList.get(0);
        }else {
            return null;
        }



    }

    class MyComparator implements Comparator<ListNode>{
        @Override
        public int compare(ListNode o1, ListNode o2) {
            return o1.val-o2.val;
        }
    }
}
   class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
