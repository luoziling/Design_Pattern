package priv.wzb.leet_code.longest_substring_without_repeating_characters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Satsuki
 * @time 2019/7/18 16:33
 * @description:
 */
public class Optimization {
//    public int lengthOfLongestSubstring(String s) {
//        int n = s.length();
//        Set<Character> set = new HashSet<>();
//        int ans = 0,i=0,j=0;
//        while (i<n&&j<n){
//            if(!set.contains(s.charAt(j))){
//                set.add(s.charAt(j++));
//                ans = Math.max(ans,j-i);
//            }else {
//                set.remove(s.charAt(i++));
//            }
//        }
//        return ans;
//    }

    /**
     * 此算法记录字符串中出现字符的位置
     * 该字符后面没有重复相同与自己的字符
     * 根据j-i+i来记录没有重复子字符的长度
     * 一旦出现重复字符i就会向后移动
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        //初始化
        int n=s.length(),ans=0;
        //用HashMap来存储字符串的各个字符以及字符在字符串中出现的位置
        Map<Character,Integer> map = new HashMap<>();// current index of character
        //j代表位置会不断的向后移，i代表会重复出现的字符串
        for (int j=0,i = 0; j < n; j++) {
            //如果map中包含相同的字符串
            if (map.containsKey(s.charAt(j))){
                //更新i的值 i会保持上一个重复字符出现的位置
                i = Math.max(map.get(s.charAt(j)),i);
            }
            //更新非重复子字符串的长度
            ans = Math.max(ans,j-i+1);
            //更新字符串中各个字符出现的位置
            map.put(s.charAt(j),j+1);
        }
        return ans;
    }


//    public int lengthOfLongestSubstring(String s) {
//        int n = s.length();
//        Set<Character> set = new HashSet<>();
//        int ans = 0, i = 0, j = 0;
//        while (i < n && j < n) {
//            // try to extend the range [i, j]
//            if (!set.contains(s.charAt(j))){
//                set.add(s.charAt(j++));
//                ans = Math.max(ans, j - i);
//            }
//            else {
//                set.remove(s.charAt(i++));
//            }
//        }
//        return ans;
//    }





//    public int lengthOfLongestSubstring(String s) {
//        int n = s.length(), ans = 0;
//        Map<Character, Integer> map = new HashMap<>(); // current index of character
//        // try to extend the range [i, j]
//        for (int j = 0, i = 0; j < n; j++) {
//            if (map.containsKey(s.charAt(j))) {
//                i = Math.max(map.get(s.charAt(j)), i);
//            }
//            ans = Math.max(ans, j - i + 1);
//            map.put(s.charAt(j), j + 1);
//        }
//        return ans;
//    }
//
//
//    public int lengthOfLongestSubstring(String s) {
//        int n = s.length(), ans = 0;
//        int[] index = new int[128]; // current index of character
//        // try to extend the range [i, j]
//        for (int j = 0, i = 0; j < n; j++) {
//            i = Math.max(index[s.charAt(j)], i);
//            ans = Math.max(ans, j - i + 1);
//            index[s.charAt(j)] = j + 1;
//        }
//        return ans;
//    }

    public static void main(String[] args) {
//        String s = "abcabcbb";
//        String s = "bbbbb";
//        String s = "pwwkew";
//        String s = "";
//        String s = " ";
//        String s = "c";
//        String s = "au";
        String s = "jbpnbwzwd";
        System.out.println(new Optimization().lengthOfLongestSubstring(s));
    }


}
