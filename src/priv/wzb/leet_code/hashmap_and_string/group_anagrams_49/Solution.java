package priv.wzb.leet_code.hashmap_and_string.group_anagrams_49;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/26 21:16
 * @description:
 * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
 *
 * 示例:
 *
 * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"],
 * 输出:
 * [
 *   ["ate","eat","tea"],
 *   ["nat","tan"],
 *   ["bat"]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/group-anagrams
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 排序数组分类
 * 利用映射将排序后的单词与这个单词所组成的列表成为一个映射
 * 遍历单词数组，每获得一个新的单词就进行单词内部的排序看看映射中是否存在该数组不存在则新建
 * 最后都需要将单词放入合适的组内完成分组返回即可
 */
public class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            // 内部排序
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            // 存在字母异位词
//            if (map.containsKey(key)){
//                map.get(key).add(s);
//            }else {
//                map.put(key,new ArrayList<>());
//                map.get(key).add(s);
//            }
            // 优化
            // 不存在
            if (!map.containsKey(key)){
                // 新增一个映射
                map.put(key,new ArrayList<>());
            }
            // 添加单词
            map.get(key).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
