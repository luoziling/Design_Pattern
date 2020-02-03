package priv.wzb.leet_code.math.palindrome_number_09;

/**
 * @author Satsuki
 * @time 2020/1/10 14:34
 * @description:
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 *
 * 示例 1:
 *
 * 输入: 121
 * 输出: true
 * 示例 2:
 *
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 *
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * 进阶:
 *
 * 你能不将整数转为字符串来解决这个问题吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public boolean isPalindrome(int x) {
        // 转为字符串再判断
        String str = String.valueOf(x);

        boolean flag = true;

        // 从中心向两边扩展注意区分奇偶
        // 这种思路需要区分奇偶且过于麻烦
        // 换一种思路，从两端向中心压缩
        // 这样就不必区分奇偶
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length()-i-1)){
                flag = false;
            }
        }

        return flag;

    }
}
