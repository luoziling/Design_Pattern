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
public class Solution1 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 头节点
        ListNode node = new ListNode(0);
        // 临时指针放丢失头节点
        ListNode ptr = node;


        while (l1!=null|| l2!=null) {
            if (l1 != null && l2 != null) {
                if (l1.val <= l2.val) {
                    node.next = l1;
                    l1 = l1.next;
                } else {
                    node.next = l2;
                    l2 = l2.next;
                }
            } else if (l2 != null) {
                node.next = l2;
                l2 = l2.next;
            } else if (l1 != null) {
                node.next = l1;
                l1 = l1.next;
            }
            node = node.next;
        }


        return ptr.next;
    }


    // 分治算法
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0){
            return null;
        }
        if (lists.length == 1){
            return lists[0];
        }
        if (lists.length == 2){
            return mergeTwoLists(lists[0],lists[1]);
        }

        int mid = lists.length/2;
        ListNode[] sub1 = new ListNode[mid];
        ListNode[] sub2 = new ListNode[lists.length-mid];
        int index = 0;
        for (int i = 0; i < mid; i++) {
            sub1[index++] = lists[i];
        }
        index = 0;
        for (int i = mid; i < lists.length; i++) {
            sub2[index++] = lists[i];
        }

        // 递归分治
        ListNode l1 = mergeKLists(sub1);
        ListNode l2 = mergeKLists(sub2);

        return mergeTwoLists(l1,l2);
    }
}


