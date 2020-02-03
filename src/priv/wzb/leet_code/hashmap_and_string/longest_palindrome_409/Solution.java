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
 * 利用map存放映射字符以及字符出现的个数的映射
 *
 */
public class Solution {
    public int longestPalindrome(String s) {
        HashMap<Character,Integer> map = new HashMap<>();
        char c;
        // 遍历字符串的字符存入hashmap
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (map.containsKey(c)){
                map.put(c,map.get(c)+1);
            }else {
                map.put(c,1);
            }
        }

        // 记录最长回文
        int maxLength = 0;
        // 记录是否有奇数字符
        boolean flag = false;
        for (Map.Entry<Character,Integer> entry:map.entrySet()){
            // 字符个数为偶数
            if (entry.getValue()%2 == 0){
                maxLength+=entry.getValue();
            }else {
                // 奇数
                // -1变为偶数
                // 偶数个字符就可以用于组成回文串
                maxLength += entry.getValue()-1;
                flag = true;
            }
        }
        // 如果存在奇数个字符
        if (flag){
            maxLength++;
        }

        return maxLength;
    }
}
