package priv.wzb.leet_code.addtwonumbers;

/**
 * @author Satsuki
 * @time 2019/11/7 23:58
 * @description: 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * <p>
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution2 {

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //定义返回的链表
        // 初始节点
        ListNode res = new ListNode(1);
        ListNode res1 = res;

        // 定义一个保存相加溢出的标识位
        boolean flag = false;
        int temp;// 相加的中间数

        //记录中间数据
        int v1, v2;
        // 当l1或者l2不为空就行
        // 因为可能两个链表的代表的数据位数是不相等的
//        while (l1!=null&& l2!=null){
        while (l1 != null || l2 != null) {
            // 这里做一个判0以访数据位数不相等 比如321 + 3这种
            v1 = l1 == null ? 0 : l1.val;
            v2 = l2 == null ? 0 : l2.val;
            // 因为是倒序存储所以正好按照加法的顺序
            temp = v1 + v2;
            // 如果之前一次相加产生进位则要再加一
            if (flag) {
                temp += 1;
                // 重置flag
                flag = false;
            }
            // 如果相加大于等于10就有进位
            if (temp >= 10) {
                // 取模
                temp = temp % 10;
                // 进位标识置true
                flag = true;
            }
            res.next = new ListNode(temp);
            res = res.next;
            // l1 l2后移
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }

        }
        // 防止处理完后还需要进位 例如 998 + 2
        if (flag) {
            res.next = new ListNode(1);
        }

        //        return res.next;
        if (res1.next != null) {
            res1 = res1.next;
        }
        return res1;
    }

    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        Solution2 solution1 = new Solution2();
//        ListNode l1 = new ListNode(2);
//        l1.next = new ListNode(4);
//        l1.next.next = new ListNode(3);
//        l1.next.next.next=null;
//
//        ListNode l2 = new ListNode(5);
//        l2.next = new ListNode(6);
//        l2.next.next = new ListNode(4);
//        l2.next.next.next=null;

//        ListNode l1 = new ListNode(5);
//        l1.next = null;
//
//        ListNode l2 = new ListNode(5);
//        l2.next = null;

        ListNode l1 = new ListNode(8);
        l1.next = new ListNode(9);
        l1.next.next = new ListNode(9);
        l1.next.next.next = null;

        ListNode l2 = new ListNode(2);
        l2.next = null;

        ListNode res = addTwoNumbers(l1, l2);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }
}
