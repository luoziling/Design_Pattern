package priv.wzb.leet_code.greedy.remove_k_digits_402;

import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/11/14 21:49
 * @description:
 */
public class Solution {
    public String removeKdigits(String num, int k) {
        // 使用栈来挑选删除后的最小序列
        LinkedList<Character> stack = new LinkedList<>();
        // 使用StringBuilder保存最后结果
        StringBuilder res = new StringBuilder();
        // 临时字符记录字符串的当前字符
        char c;
        boolean flag = false;
        // 遍历字符串的字符
        for (int i = 0; i < num.length(); i++) {
            flag = false;
            // 没必要转为数字 ，因为ascii码的字符也是可以分大小的且顺序与0-9的顺序一致
            c = num.charAt(i);
            // 当栈不为空，且k>0代表还有数字要删除，且要添加的元素小于栈顶元素
            while (stack.size()!=0&&c<stack.peek()&&k>0){
                // 弹栈
                stack.pop();
                // 不能在此入栈否则出错造成多次入栈
                // 入栈新元素
//                stack.push(c);
                // 已经减少一个数字
                k--;
            }
//            if (stack.size()==0||c>=stack.peek()){
//                // 栈空直接入栈
//                stack.push(c);
//            }
            // 去除掉头节点为0的特殊情况
            // 若头节点为0就不入栈
            if (c!='0'||stack.size()!=0){
                stack.push(c);
            }
        }
        // 处理特殊情况
        // 当栈不为空，且未删除完成
        while (stack.size()!=0&&k>0){
            // 直接弹出栈顶元素即可
            // 根据上面的遍历，较大的元素肯定在靠近栈顶的位置
            // 只有一种情况会造成这样，这个字符串是一个升序排列的
            stack.pop();
            // 减少一个字符
            k--;
        }
        // 连接
        while (stack.size()>0){
            // 应该从栈底开始取
            res.append(stack.getLast());
            // 弹出栈底
            stack.removeLast();
        }


        // 如果空则返回0
        // 这里会出错，res是StringBuilder对象而""并不属于StringBuilder对象
        // 转为String后再比较
        if (res.toString().equals("")){
            return "0";
        }
        return res.toString();

    }

    public static void main(String[] args) {
        System.out.println(new Solution().removeKdigits("1432219",3));
    }
}
