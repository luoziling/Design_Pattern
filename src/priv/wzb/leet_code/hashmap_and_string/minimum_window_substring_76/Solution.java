package priv.wzb.leet_code.hashmap_and_string.minimum_window_substring_76;

/**
 * @author Satsuki
 * @time 2019/11/28 20:53
 * @description:
 * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
 *
 * 示例：
 *
 * 输入: S = "ADOBECODEBANC", T = "ABC"
 * 输出: "BANC"
 * 说明：
 *
 * 如果 S 中不存这样的子串，则返回空字符串 ""。
 * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-window-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 利用滑动窗口+判断是否出现
 */
public class Solution {
    public String minWindow(String s, String t) {
        String res = "";
        String subString;
        int left = 0;
        int right = 1;
        while (left<right&&right<=s.length()){
            subString = s.substring(left,right);
            while (subString.contains(t)){
                if (res.length()==0||subString.length()<res.length()){
                    res = subString;
                }
                left++;
            }
            right++;
        }
        return res;
    }

    /**
     * 判断t中字符是否在a中全部出现
     * @param a
     * @param t
     * @return
     */
//    boolean appear(String a,String t){
//        char[] chars = a.toCharArray();
//        a.con
//    }
}
