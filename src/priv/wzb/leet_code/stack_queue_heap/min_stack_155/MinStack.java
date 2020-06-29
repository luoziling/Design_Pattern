package priv.wzb.leet_code.stack_queue_heap.min_stack_155;

import java.util.Stack;

/**
 * @author Satsuki
 * @time 2020/5/13 23:01
 * @description:
 */
public class MinStack {
    /** initialize your data structure here. */
    private Stack<Integer> dataStack;
    private Stack<Integer> minStack;
    public MinStack() {
        dataStack = new Stack<>();
        minStack = new Stack<>();

    }

    public void push(int x) {
        dataStack.push(x);
        if (minStack.isEmpty()||x<=minStack.peek()){
            minStack.push(x);
        }
    }

    public void pop() {
        int pop = dataStack.pop();
        if (pop == minStack.peek()){
            minStack.pop();
        }
    }

    public int top() {
        return dataStack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
