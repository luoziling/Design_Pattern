package priv.wzb.leet_code.addtwonumbers;

/**
 * @author Satsuki
 * @time 2019/7/17 16:20
 * @description:
 */

import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */


class Solution {
    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int x){
            val=x;
        }
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int n1,n2;
        n1=n2=0;
        n1=myLen(l1);
        n2=myLen(l2);
        System.out.println("n1:" + n1 + "-" + "n2:" + n2);
        int n;
        if(n1>n2){
            n=n1;
        }else if(n1<n2){
            n=n2;
        }else{
            n=n1;
        }
        ListNode res = new ListNode(0);
        ListNode res1 = res;
        ListNode res2 = res1;
        //将进位 carrycarry 初始化为 00。
        boolean carry=false;
        int nval=0;
        //遍历列表 l1l1 和 l2l2 直至到达它们的尾端。
        for (int i = 0; i < n; i++) {
            //设定 sum = x + y + carrysum=x+y+carry。

            nval = l1.val+l2.val;
            if(carry){
                nval++;
            }

            carry=false;
            if(nval>=10){
                //更新进位的值，carry = sum / 10carry=sum/10。
                //进位标志置为true
                carry=true;
                //取个位数字
                nval = nval%10;
            }
            //创建一个数值为 (sum \bmod 10)(summod10) 的新结点，并将其设置为当前结点的下一个结点，然后将当前结点前进到下一个结点。
            res.val=nval;
            res.next = new ListNode(0);
            res = res.next;
//            if(i!=n){
//                res.next = new ListNode(0);
//                res = res.next;
//            }else {
//                res.next=null;
//            }
//            res.next = new ListNode(0);
//            res = res.next;

            //将 xx 设为结点 pp 的值。如果 pp 已经到达 l1l1 的末尾，则将其值设置为 00。
            //将 yy 设为结点 qq 的值。如果 qq 已经到达 l2l2 的末尾，则将其值设置为 00。

            //同时，将 pp 和 qq 前进到下一个结点。
            //如果下一个元素存在则取下一个
            //如果不存在则取0
            if(l1.next!=null){
                l1=l1.next;
            }else {
                l1.val=0;
            }
            if(l2.next!=null){
                l2=l2.next;
            }else {
                l2.val=0;
            }
            System.out.println(nval);
//            if(i==n){
//                res=null;
//            }
        }
//        res = null;
        //检查 carry = 1carry=1 是否成立，如果成立，则向返回列表追加一个含有数字 11 的新结点。
        if(carry==true){
//            res = new ListNode(1);
            res.val=1;
            res.next=null;


//            res.next = new ListNode(0);
//            res = res.next;
//            res.val=1;
//            res.next=null;
//            res = res.next;
        }

//        res=null;
//        res.next=null;
//        ListNode res2 = res1;

//        while (res1.next.next!=null){
//            res1 = res1.next;
//        }

        if(carry==false){
            while (res1.next.next!=null){
                res1 = res1.next;
            }
            res1.next=null;
        }else {
            while (res1.next!=null){
                res1 = res1.next;
            }
            res1.next=null;
        }


//        if(carry==true){
//            res = new ListNode(1);
//            res.next=null;
//        }
//        res1=null;
//        res1.next=null;
//        res2=null;
        return res2;
    }

    public static int myLen(ListNode len){
        int n=0;
//        while (len.next!=null){
//            n++;
//            System.out.println(n);
//            len=len.next;
//        }
        while (len!=null){
            n++;
            len=len.next;
        }
        return n;
    }

    public static void main(String[] args) {

        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        l1.next.next.next=null;

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        l2.next.next.next=null;

//        ListNode l1 = new ListNode(5);
//        l1.next = null;
//
//        ListNode l2 = new ListNode(5);
//        l2.next=null;

//        while (l1.next!=null){
//            System.out.println(l1.val);
//            l1= l1.next;
//        }

        ListNode res = new Solution().addTwoNumbers(l1,l2);
        while (res.next!=null){
            System.out.println(res.val);
            res = res.next;
        }
    }
}
