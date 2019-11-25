package priv.wzb.leet_code.longest_palindromic_Substring;

import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/7/22 16:44
 * @description:
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 *
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 *
 * 输入: "cbbd"
 * 输出: "bb"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 思路：
 * 使用堆栈，入栈一半再出来和另一半对比，其实花费的时间还是O（n）
 * 重点在于对于是否是会问数组的判定，如何使用动态规划法
 * 如果判定到一个回文字符串，那么在下一次判定可以判定这个字符串的前后是否加入了相同的字符
 * 加入了就说明是回文字符串，没加入就说明不是
 * 借助滑动窗口来遍历 （队列）
 * 暴力破解
 */
public class Violence {

    public String longestPalindrome(String s) {
        int max = 0;
        String palindrome = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i+1; j <= s.length(); j++) {
                if (isPalindrome(s.substring(i,j))){
                    if ((j-i)>max){
                        max = j-i;
                        palindrome = s.substring(i,j);
                    }
                }
            }
        }
        return palindrome;
    }

    public boolean isPalindrome(String s){
        for (int i = 0; i < s.length()/2; i++) {
            if (s.charAt(i)!=s.charAt(s.length()-i-1)){
                return false;
            }
        }
        return true;
    }



    public static void main(String[] args) {
        String s = "babad";
//        String s = "bb";
//        String s = "b";


//        isPalindromic("aaa");
        System.out.println(new Violence().longestPalindrome(s));
    }
}
