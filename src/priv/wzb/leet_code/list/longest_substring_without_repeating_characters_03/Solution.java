package priv.wzb.leet_code.list.longest_substring_without_repeating_characters_03;

import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/12/3 21:14
 * @description:
 */
public class Solution {
    public int lengthOfLongestSubstring(String s) {
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
}
