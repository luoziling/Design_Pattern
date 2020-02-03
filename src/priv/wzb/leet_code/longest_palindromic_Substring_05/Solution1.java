package priv.wzb.leet_code.longest_palindromic_Substring_05;

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
public class Solution1 {

    public String longestPalindrome(String s) {
        LinkedList<Character> charList = new LinkedList<>();
        // 辅助
        LinkedList<Character> charList1 = new LinkedList<>();
        // 利用滑动窗口存储数组再判定回文
        for (int i = 0; i < s.length(); i++) {
            charList.add(s.charAt(i));
        }
        int maxSize = 0;
        StringBuilder palindrome = new StringBuilder();
        // 单个字符也是回文
        while (charList.size()>0){
            // 判断自身
            if (isPalindrome(charList)){
                while (charList.size()>0){
                    palindrome.append(charList.removeFirst());
                }
                maxSize = charList.size();
                break;
            }

            // 左边减一个再判断
            charList1 = new LinkedList<>(charList);
            // 不可以指向堆中的同一个对象
//            charList1 = charList;
            charList1.removeFirst();
            if (isPalindrome(charList1)){
                charList.removeFirst();
                while (charList.size()>0){
                    palindrome.append(charList.removeFirst());
                }
                maxSize = charList.size();
                break;
            }

            // 右边减一个再判断
            charList1 = new LinkedList<>(charList);
            charList1.removeLast();
            if (isPalindrome(charList1)){
                charList.removeLast();
                while (charList.size()>0){
                    palindrome.append(charList.removeFirst());
                }
                maxSize = charList.size();
                break;
            }

            // 若都未找到了左右各移除一个继续判定
            charList.removeFirst();
            charList.removeLast();
        }

        return palindrome.toString();

    }

    public boolean isPalindrome(LinkedList<Character> subList){
        // 判定到中间即可
        for (int i = 0; i < subList.size()/2; i++) {
//        for (int i = 0; i < subList.size(); i++) {
            if (!subList.get(i).equals(subList.get(subList.size()-i-1)) ){
//            if (subList.get(i) != subList.get(subList.size()-i-1)){
                return false;
            }
        }
        return true;
    }



    public static void main(String[] args) {
//        String s = "babad";
//        String s = "bb";
        String s = "b";


//        isPalindromic("aaa");
        System.out.println(new Solution1().longestPalindrome(s));
    }
}
