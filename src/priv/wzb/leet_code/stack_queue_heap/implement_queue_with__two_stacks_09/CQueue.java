package priv.wzb.leet_code.stack_queue_heap.implement_queue_with__two_stacks_09;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author Satsuki
 * @time 2020/6/30 14:52
 * @description:
 */
public class CQueue {
//    private Stack<Integer> integerStack;
//    private Stack<Integer> integerStack1;
//    public CQueue() {
//        integerStack = new Stack<>();
//    }
//
//    public void appendTail(int value) {
//        integerStack.push(value);
//    }
//
//    public int deleteHead() {
//
//        try {
//            integerStack1 = new Stack<>();
//            for (Integer integer : integerStack) {
//                integerStack1.push(integer);
//            }
//            if (integerStack.size()>0){
//                return integerStack1.pop();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 将更改的栈写回原来的栈
//            integerStack = new Stack<>();
//            for (Integer integer : integerStack1) {
//                integerStack.push(integer);
//            }
//        }
//        return -1;
//
//    }

    Deque<Integer> stack1;
    Deque<Integer> stack2;


    public CQueue() {
        stack1 = new LinkedList<Integer>();
        stack2 = new LinkedList<Integer>();
    }

    public void appendTail(int value) {
        stack1.push(value);
    }

    public int deleteHead() {
        // 如果第二个栈为空
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            return -1;
        } else {
            int deleteItem = stack2.pop();
            return deleteItem;
        }
    }

}
