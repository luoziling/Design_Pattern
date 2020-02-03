package priv.wzb.leet_code.math.palindrome_number_09;

/**
 * @author Satsuki
 * @time 2020/1/10 14:34
 * @description: 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * 进阶:
 * <p>
 * 你能不将整数转为字符串来解决这个问题吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 数字反转
 * 如果全部反转会出现溢出情况
 * 考虑回文特性，反转一半判断
 */
public class Solution1 {
    public boolean isPalindrome(int x) {
        // 去除特殊情况
        // x<0时，x不是回文数
        // 同样，如果数字的最后一位是0，只有第一位也是0，只有0符合这种情况
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        // 由于是回文数的判定，从后取取一半
        // 取就意味着原始数字不停/10反转后的数字不停*10
        // 判断取一半的条件就是当反转后的数字大于等于原始数字就意味着到了一半
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        // 当数字长度为奇数，可以通过revertedNumber/10去除处于中位的数字。
        // 例如，当输入为 12321 时，在 while 循环的末尾我们可以得到 x = 12，revertedNumber = 123，
        // 由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单的将其去除
        return x == revertedNumber || x == revertedNumber / 10;
    }

    public static void main(String[] args) {
        System.out.println(new Solution1().isPalindrome(121));
    }
}
