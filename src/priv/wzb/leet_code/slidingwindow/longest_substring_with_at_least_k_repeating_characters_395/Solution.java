package priv.wzb.leet_code.slidingwindow.longest_substring_with_at_least_k_repeating_characters_395;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/27 13:46
 * @since 1.0.0
 */
public class Solution {
    public int longestSubstring(String s, int k) {
        int ret = 0;
        int n = s.length();
        // 统计26中type ，多种type的字符数量
        for (int t = 0; t <= 26; t++) {
            int l = 0,r = 0;
            // cnt统计每个字符出现的次数
            int[] cnt = new int[26];
            // 字符种类
            int tot = 0;
            // 计数器当前出现次数小于k的字符的数量
            int less = 0;
            while (r<n){
                cnt[s.charAt(r) - 'a']++;
                if (cnt[s.charAt(r) - 'a'] == 1){
                    // 第一次出现某个字符，种类+1
                    tot++;
                    // 且less与k的种类也+1
                    less++;
                }
                if (cnt[s.charAt(r) - 'a'] == k){
                    // 出现的字符数第一次累计到符合条件的k个，说明不满足的种类自减
                    less--;
                }
                // 超出预定的种类,右移左边界并且更新种类数量，知道符合统计规律
                while (tot > t){
                    cnt[s.charAt(l) - 'a']--;
                    if (cnt[s.charAt(l) - 'a'] == k-1){
                        less++;
                    }
                    if (cnt[s.charAt(l) - 'a'] == 0){
                        // 某一种字符计数为空
                        tot--;
                        less--;
                    }
                    l++;
                }
                if (less == 0) {
                    // 有多种类型且每种类型都超过预定k个
                    ret = Math.max(ret,r-l+1);
                }
                r++;
            }
        }
        return ret;
    }
}
