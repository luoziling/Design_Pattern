package priv.wzb.datastructure.linkedlist;

import java.util.Stack;

/**
 * @author Satsuki
 * @time 2019/10/29 19:54
 * @description:
 * 演示栈的基本使用
 */
public class TestStack {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        // 入栈
        stack.add("jack");
        stack.add("tom");
        stack.add("smith");

        // 出栈
        while (stack.size()>0){
            System.out.println(stack.pop());
        }
    }
}
