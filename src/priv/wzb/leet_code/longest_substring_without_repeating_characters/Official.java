package priv.wzb.leet_code.longest_substring_without_repeating_characters;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Satsuki
 * @time 2019/7/18 16:24
 * @description:
 */
public class Official {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j <=n ; j++) {
                if(allUnique(s,i,j)) ans = Math.max(ans,j-i);
            }
        }
        return ans;
    }

    public boolean allUnique(String s,int start,int end){
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            Character ch = s.charAt(i);
            if(set.contains(ch)) return false;
            set.add(ch);
        }
        return true;
    }

    public static void main(String[] args) {
//        String s = "abcabcbb";
//        String s = "bbbbb";
//        String s = "pwwkew";
//        String s = "";
//        String s = " ";
//        String s = "c";
//        String s = "au";
        String s = "jbpnbwwd";
        System.out.println(new Solution().lengthOfLongestSubstring(s));
    }
}
