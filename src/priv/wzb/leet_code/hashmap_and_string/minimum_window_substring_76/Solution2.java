package priv.wzb.leet_code.hashmap_and_string.minimum_window_substring_76;

import java.util.HashMap;
import java.util.Map;

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
public class Solution2 {
    public String minWindow(String s, String t) {
        if (s.length() == 0 || t.length()==0){
            return "";
        }
        // 记录t中出现的所有每个字符的个数
        Map<Character,Integer> dicT = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            // getOrDefault返回指定的value没有则返回默认值
            int count = dicT.getOrDefault(t.charAt(i),0);
            dicT.put(t.charAt(i),count+1);
        }

        // t中出现的字符数（多个相同的当一个
        int required = dicT.size();

        // 左右指针
        int l = 0,r=0;
        // 用来追踪t中有多少个唯一字符
        // 在当前窗口中以期望的频率出现
        // 例如， 如果t为“AABC”，则窗口必须具有两个A一个B和一个C
        // 当满足时=3
        int formed = 0;

        // 字典，保存当前窗口中所有唯一字符的计数
        Map<Character,Integer> windowsCounts = new HashMap<>();

        // 表格ans列表（窗口i长度，左指针，有指针）
        int[] ans = {-1,0,0};
        while (r<s.length()){
            char c = s.charAt(r);
            int count = windowsCounts.getOrDefault(c,0);
            windowsCounts.put(c,count+1);

            if (dicT.containsKey(c)&&windowsCounts.get(c).intValue() == dicT.get(c).intValue()){
                formed++;
            }

            while (l<=r && formed == required){
                c = s.charAt(l);
                if (ans[0] == -1 || r-l + 1<ans[0]){
                    ans[0] = r-l+1;
                    ans[1] = l;
                    ans[2] = r;
                }
                windowsCounts.put(c,windowsCounts.get(c)-1);
                if (dicT.containsKey(c)&& windowsCounts.get(c).intValue()<dicT.get(c).intValue()){
                    formed--;
                }
                l++;
            }
            r++;

        }
        return ans[0] == -1? "":s.substring(ans[1],ans[2]+1);

    }


}
