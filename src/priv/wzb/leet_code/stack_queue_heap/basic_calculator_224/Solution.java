package priv.wzb.leet_code.stack_queue_heap.basic_calculator_224;

import javax.print.attribute.standard.NumberUp;
import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/11/10 16:43
 * @description: 实现一个基本的计算器来计算一个简单的字符串表达式的值。
 * <p>
 * 字符串表达式可以包含左括号 ( ，右括号 )，加号 + ，减号 -，非负整数和空格  。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "1 + 1"
 * 输出: 2
 * 示例 2:
 * <p>
 * 输入: " 2-1 + 2 "
 * 输出: 3
 * 示例 3:
 * <p>
 * 输入: "(1+(4+5+2)-3)+(6+8)"
 * 输出: 23
 * 说明：
 * <p>
 * 你可以假设所给定的表达式都是有效的。
 * 请不要使用内置的库函数 eval。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/basic-calculator
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {

    // 数字栈
    LinkedList<Integer> numList = new LinkedList<>();
    // 符号栈
    LinkedList<Character> charList = new LinkedList<>();

    int num1 = 0, num2 = 0;

    Character top = null;

    // 保存遍历时的字符
    char c;

    public int calculate(String s) {
        // 去空格
        s = s.replace(" ", "");
        // 处理只有数字的情况
        if (s.matches("\\d+")){
            return Integer.parseInt(s);
        }

        // 将输入的中缀表达式转后缀，逆波兰表达式
        // 还用不到逆波兰表达式
        // 直接借助两个栈进行制作
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            // 因为只有加减所以不需要判定运算符优先级
            if (c >= '0' && c <= '9') {
                // 数字直接入数字栈
                numList.push(c - '0');
                // 判断是否多个数字串在一起
                // 不断后移
                // 在不越界的情况下
                while (i+1<s.length()&&(s.charAt(i+1)>='0' && s.charAt(i+1)<='9')){
                    // 后一个依旧是数字
                    // 记得减'0'
                    numList.push(numList.pop()*10+(s.charAt(i+1)-'0'));
                    i++;
                }
            } else if (charList.isEmpty()) {
                // 如果符号栈空则直接入符号栈
                charList.push(s.charAt(i));
            } else {
                // 遇到左括号直接入符号栈
                if (c == '(') {
                    charList.push(s.charAt(i));
                } else if (c == ')') {
                    // 遇到右括号则弹出一个符号栈栈顶元素与两个数字栈元素运算并将结果存入数字栈
                    // 直至到达左括号
                    top = charList.pop();
                    while (top != '(') {
                        num1 = numList.pop();
                        num2 = numList.pop();
                        if (top == '+') {
                            numList.push(num1 + num2);
                        } else if (top == '-') {
                            // 减法的话也是后面一个出栈的元素对前一个出栈的进行运算
                            numList.push(num2 - num1);
                        } else {
                            // 符号不对
                            return -1;
                        }
                        cal();
                        // 继续弹出运算符
                        top = charList.pop();
                    }
                } else if (c == '+' || c == '-') {
                    // 如果是普通+ -就取出符号栈栈顶元素与数字栈两个元素运算将结果压入数字栈
                    // 新的符号也压入符号栈
                    cal();
                    // 符号栈压入新元素
                    charList.push(c);

                }
            }
        }
        while (!charList.isEmpty()) {
            // 符号栈不为空则继续运算
            cal();
        }
        return numList.get(0);

//        return -1;
    }

    public void cal() {
        // 只有当符号栈栈顶元素是加减才要弹出
        if (charList.peek() == '+' || charList.peek() == '-') {
            num1 = numList.pop();
            num2 = numList.pop();
            top = charList.pop();
            if (top == '+') {
                numList.push(num1 + num2);
            } else if (top == '-') {
                // 减法的话也是后面一个出栈的元素对前一个出栈的进行运算
                numList.push(num2 - num1);
            } else {
                // 符号不对
                return;
            }
        }

    }

    public static void main(String[] args) {
        String s = "1 + 1";
        s = "(1+(4+5+2)-3)+(6+8)";
        s="1-11";
        System.out.println(new Solution().calculate(s));
    }
}
