package priv.wzb.leet_code.qianxin;

import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/11/9 0:32
 * @description:
 * 队列和栈的关键就在于FIFO和FILO
 * https://leetcode-cn.com/problems/implement-stack-using-queues/solution/yong-dui-lie-shi-xian-zhan-by-leetcode/
 */
class MyStack {
    LinkedList<Integer> list;

    /** Initialize your data structure here. */
    public MyStack() {
        list = new LinkedList<>();

    }

    /** Push element x onto stack. */
    public void push(int x) {
        // 按顺序进栈
        list.add(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        if (!empty()){
            // 逆序出栈
//            return list.removeFirst();
            return list.removeLast();
        }
        return -1;
    }

    /** Get the top element. */
    public int top() {
        if (!empty()){
            return list.getLast();
        }
        return -1;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return list.size()==0;
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
