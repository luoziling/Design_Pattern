package priv.wzb.leet_code.hashmap_and_string.Valid_Palindrome_II;

/**
 * @author Satsuki
 * @time 2020/5/19 0:43
 * @description:给定一个非空字符串 s，最多删除一个字符。判断是否能成为回文字符串。
 *
 * 示例 1:
 *
 * 输入: "aba"
 * 输出: True
 * 示例 2:
 *
 * 输入: "abca"
 * 输出: True
 * 解释: 你可以删除c字符。
 * 注意:
 *
 * 字符串只包含从 a-z 的小写字母。字符串的最大长度是50000。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-palindrome-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class Solution {
    public boolean validPalindrome(String s) {
        // 贪心的双指针验证回文
        int low = 0,high = s.length()-1;
        // 循环尝试移除左边或者右边的字符
        while (low<high){
            // 初始化可能移除的字符(同样也是左右两个端点指针指向的字符
            // 两端不断夹逼
            char c1 = s.charAt(low),c2 = s.charAt(high);
            if (c1==c2){
                // 相等则移动指针继续下一次判断
                low++;
                high--;
            }else {
                // 尝试移除左边或者右边
                boolean flag1 = true,flag2 = true;
                // i,j代表移除某个字符后的左右双指针
                // 这边移除右侧的字符
                for (int i = low,j=high-1; i < j; i++,j--) {
                    char c3 = s.charAt(i),c4 = s.charAt(j);
                    if (c3!=c4){
                        // 移除一个字符后仍然无法回文
                        flag1 = false;
                        break;
                    }
                }
                for (int i = low+1,j=high; i < j; i++,j--) {
                    char c3 = s.charAt(i),c4 = s.charAt(j);
                    if (c3!=c4){
                        // 移除一个字符后仍然无法回文
                        flag2 = false;
                        break;
                    }
                }

                // 移除左边或右边能成就是回文
                return flag1 || flag2;
            }
        }
        // 不用移除就是回文直接返回true
        return true;
    }
}
