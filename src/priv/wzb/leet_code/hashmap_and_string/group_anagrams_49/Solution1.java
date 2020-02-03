package priv.wzb.leet_code.hashmap_and_string.group_anagrams_49;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/26 21:31
 * @description:
 * 使用26个英文字符据每个单词出现的字母产生一个有序模式，作为key
 * value还是一个数组存储匹配这个模式的单词们
 */
public class Solution1 {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 判零
        if (strs.length == 0) return new ArrayList<>();
        Map<String,List<String>> map = new HashMap<>();
        int[] count = new int[26];
        // 遍历
        for (String s: strs){
            // 填充计数/初始化计数数组用于记录字母出现次数
            Arrays.fill(count,0);
            // 计数
            for(char c : s.toCharArray()) count[c-'a']++;
            // 组合
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                sb.append("#");
                sb.append(count[i]);
            }
            // 组合出来的匹配模式
            String key = sb.toString();
            if (!map.containsKey(key)) map.put(key,new ArrayList());
            map.get(key).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
