package priv.wzb.leet_code.stack_queue_heap.validate_stack_sequences_946;

import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/11/10 15:41
 * @description:
 * 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 * 输出：true
 * 解释：我们可以按以下顺序执行：
 * push(1), push(2), push(3), push(4), pop() -> 4,
 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 * 示例 2：
 *
 * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 * 输出：false
 * 解释：1 不能在 2 之前弹出。
 *  
 *
 * 提示：
 *
 * 0 <= pushed.length == popped.length <= 1000
 * 0 <= pushed[i], popped[i] < 1000
 * pushed 是 popped 的排列。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/validate-stack-sequences
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    // 使用linkedList模拟堆栈
    LinkedList<Integer> list = new LinkedList<>();
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        // 把pushed看作栈，把poped看作队列
        // 按照指定顺序入栈和出栈模拟栈看看是否合法
        int length = pushed.length;
        int j = 0;

        // j初始值为0也就是要弹出的第一个元素
        for (int i = 0; i < length; i++) {
            // 不停的让入栈顺序的元素入栈
            list.push(pushed[i]);
            // 当找到第一个要出栈的元素就让j向后移模拟出栈队列pop
            while (list.peek()!=null&&(list.peek() == popped[j])){
            // 要进行判空否则会出现NullException
//            while (list.peek() == popped[j]){
                // 弹出队首元素
                // 栈后移，队列前移
                j++;
                list.pop();
            }
        }

        // 未全部弹出
        // 当j遍历到出栈队列的末尾代表全部弹出
        if (j!=length){
            return false;
        }
        // 全部出栈并且合法
        return true;
    }

    public static void main(String[] args) {
//        int[] pushed = {1,2,3,4,5};
//        int[] poped = {4,5,3,2,1};
        int[] pushed = {1,2,3,4,5};
        int[] poped = {4,3,5,1,2};

        System.out.println(new Solution().validateStackSequences(pushed,poped));
    }
}
