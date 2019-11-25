package priv.wzb.leet_code.longest_substring_without_repeating_characters;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/7/18 15:18
 * @description:
 */
public class Solution1 {

    /**
     * 返回最长无重复子字符串长度
     *
     * @param s
     * @return
     *
     * 尝试利用滑动窗口
     * 尝试利用队列 在Java中的队列就是LinkList
     * 先进先出，
     */
    public int lengthOfLongestSubstring(String s) {
        // 字符串为空直接返回 ：首先就需要是对该进行操作的值进行一个判定以防意外
        if (s.length() == 0){
            return 0;
        }
        LinkedList<Character> charList = new LinkedList<>();
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            // 如果队列为空则直接入队
            if (charList.size()==0){
                charList.add(s.charAt(i));
                // max置一
                max = 1;
            }else {
                // 判定队列中元素是否重复
                while (charList.contains(s.charAt(i))){
                    // 只要有重复就将队首元素出队
                    charList.removeFirst();
                }
                // 元素入队
                charList.add(s.charAt(i));
                // 判定无重复元素的队列中有多少个元素就是无重复的子队列
                // 通过不停的判定从而获得最长无重复字符串长度
                if (charList.size()>max){
                    max = charList.size();
                }
            }
        }
        return max;
    }

    // 在最初的思想上尝试进行优化
    public int lengthOfLongestSubstring4(String s) {
        // 字符串为空直接返回 ：首先就需要是对该进行操作的值进行一个判定以防意外
        if (s.length() == 0){
            return 0;
        }
        LinkedList<Character> charList = new LinkedList<>();
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            while (charList.contains(s.charAt(i))){
                // 一定要从头节点开始移除， 滑动窗口
                charList.removeFirst();
            }
            charList.add(s.charAt(i));
            if (charList.size()>max){
                max = charList.size();
            }
        }
        return max;
    }

    public int lengthOfLongestSubstring1(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;

    }

    public int lengthOfLongestSubstring2(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "abcabcbb";
//        String s = "bbbbb";
//        String s = "pwwkew";
//        String s = "";
//        String s = " ";

//        String s = "c";
//        String s = "au";
//        String s = "jbpnbwwd";
        System.out.println("s.length:" + s.length());
        System.out.println(new Solution1().lengthOfLongestSubstring1(s));
    }
}
