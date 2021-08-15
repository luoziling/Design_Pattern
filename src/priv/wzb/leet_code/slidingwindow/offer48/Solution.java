package priv.wzb.leet_code.slidingwindow.offer48;

import java.util.LinkedList;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/8/12 23:01
 * @description:
 * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 * @since 1.0.0
 */
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int max = 0;
        char[] chars = s.toCharArray();
        // 初始化左右两侧 作为滑动窗口
        int left=0,right = 0;
        // 用于判断窗口中是否由重复元素
        LinkedList<Character> list =  new LinkedList<>();
        for (int i = 0; i < chars.length; i++) {
            // 发现重复则移动left
            if (list.contains(chars[i])){
                while (list.contains(chars[i])){
                    left++;
                    list.removeFirst();
                }

            }
            right++;
            list.add(chars[i]);
            if (right-left>max){
                max = right-left;
            }
        }
        return max;
    }
}
