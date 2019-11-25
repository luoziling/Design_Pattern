package priv.wzb.leet_code.addtwonumbers;

/**
 * @author Satsuki
 * @time 2019/11/7 23:58
 * @description:
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution1 {

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //定义返回的链表
        // 初始节点
        ListNode res = new ListNode(1);
        ListNode res1 = res;
        // 定义一个代表位置的数字（好像不需要
        int mult = 1;
        // 定义一个保存相加溢出的标识位
        boolean flag = false;
        int temp;// 相加的中间数
        while (l1!=null&& l2!=null){
            // 因为是倒序存储所以正好按照加法的顺序
            temp = l1.val + l2.val;
            // 如果之前一次相加产生进位则要再加一
            if (flag){
                temp +=1;
                // 重置flag
                flag = false;
            }
            // 如果相加大于等于10就有进位
            if (temp>=10){
                // 取模
                temp = temp%10;
                // 进位标识置true
                flag = true;
            }
            res.next = new ListNode(temp);
            res = res.next;
            // l1 l2后移
            l1 = l1.next;
            l2 = l2.next;
        }
        // 如果最后一次加玩发现大于等于10了需要处理例如5 + 5 =10 ，1并没有加入到listNode
//        if (flag){
//            res.next = new ListNode(1);
//            res = res.next;
//        }
        // 如果是不同位数的将剩余位数归位
        while (l1!=null){
            if (flag){

                int temp1 = res.val+l1.val+1;
                if (temp1<10 && l1.next!=null){
                    res.val = temp1;
                    flag = false;
                }else {
                    temp1 = temp1%10;
                }
            }else {
                res.next = new ListNode(l1.val);
            }
            // 后移
            l1 = l1.next;
            res = res.next;
            if (l1!=null&&res==null){
                res = new ListNode(0);
            }
        }
        while (l2!=null){
            if (flag){
//                res.val = res.val+l2.val;
//                flag = false;
                int temp1 = res.val+l2.val+1;
                if (temp1<10 && l2.next!=null){
                    res.val = temp1;
                    flag = false;
                }else {
                    temp1 = temp1%10;
                }
            }else {
                res.next = new ListNode(l2.val);
            }
//            res.next = new ListNode(l2.val);
            // 后移
            l2 = l2.next;
            res = res.next;
            if (l2!=null&&res==null){
                res = new ListNode(0);
            }
        }

//        return res.next;
        if (res1.next!=null){
            res1 = res1.next;
        }

        return res1;
    }
    public static class ListNode {

        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
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
        l1.next.next.next=null;

        ListNode l2 = new ListNode(2);
        l2.next = null;

        ListNode res = addTwoNumbers(l1,l2);
        while (res!=null){
            System.out.println(res.val);
            res = res.next;
        }
    }
}
