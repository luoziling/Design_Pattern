package priv.wzb.leet_code.recursive_backtracking_divide.generate_parentheses_22;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/20 16:25
 * @description:
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 *
 * 例如，给出 n = 3，生成结果为：
 *
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 使用递归和回溯进行解决
 * 一共有n对括号那么由括号组成的字符串的长度就是2n
 * 一共的可能性就是2^2n
 * 还需要进行筛选，左括号必须优先于右括号放置
 */
public class Solution {
    List<String> result = new LinkedList<>();
    public List<String> generateParenthesis(int n) {
        // 生成括号
        generate("",n ,n);
        // 结果返回
        return result;
    }

    /**
     *
     * @param item 存放中间结果（合法的括号字符串
     * @param left 左括号剩余个数
     * @param right 右括号剩余个数
     */
    public void generate(String item,int left,int right){
        if (left == 0 && right == 0){
            result.add(item);
            return;
        }
        // 左括号还有剩余的可以放置
        if (left>0){
            generate(item+'(',left-1,right);
        }
        // 为了使括号字符串合法左括号必须在右括号之前进行放置
        // 如果左括号剩余数量小于右括号剩余数量
        // 说明左括号放置过了可以放右括号
        if (right>left){
            generate(item+')',left,right-1 );
        }
    }
}
