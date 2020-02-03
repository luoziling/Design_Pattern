package priv.wzb.leet_code.hashmap_and_string.longest_palindrome_409;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/11/26 20:20
 * @description:
 * 给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
 *
 * 在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。
 *
 * 注意:
 * 假设字符串的长度不会超过 1010。
 *
 * 示例 1:
 *
 * 输入:
 * "abccccdd"
 *
 * 输出:
 * 7
 *
 * 解释:
 * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindrome
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 利用字符哈希，ascii一共128个字符
 *
 */
public class Solution1 {
    public int longestPalindrome(String s) {
        // 字符哈希
        int[] charMap = new int[128];
        // 记录最长回文
        int maxLength = 0;
        // 记录是否有奇数字符
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            charMap[s.charAt(i)]++;
        }
        for (int i = 0; i < 128; i++) {
            if (charMap[i]%2==0){
                // 偶数情况
                maxLength += charMap[i];
            }else {
                maxLength+=charMap[i]-1;
                flag = true;
            }
        }
        if (flag){
            maxLength++;
        }


        return maxLength;
    }
}
