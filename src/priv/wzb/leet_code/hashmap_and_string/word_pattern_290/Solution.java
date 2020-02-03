package priv.wzb.leet_code.hashmap_and_string.word_pattern_290;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/11/26 20:52
 * @description:
 * 给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。
 *
 * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。
 *
 * 示例1:
 *
 * 输入: pattern = "abba", str = "dog cat cat dog"
 * 输出: true
 * 示例 2:
 *
 * 输入:pattern = "abba", str = "dog cat cat fish"
 * 输出: false
 * 示例 3:
 *
 * 输入: pattern = "aaaa", str = "dog cat cat dog"
 * 输出: false
 * 示例 4:
 *
 * 输入: pattern = "abba", str = "dog dog dog dog"
 * 输出: false
 * 说明:
 * 你可以假设 pattern 只包含小写字母， str 包含了由单个空格分隔的小写字母。    
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/word-pattern
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 利用hashmap来记录pattern和字符串进行匹配
 * 模式的字符与单词相互映射匹配
 */
public class Solution {
    public boolean wordPattern(String pattern, String str) {
        String[] s = str.split(" ");
        // 长度不匹配直接返回false
        if (pattern.length()!=s.length)
            return false;

        // 记录pattern与单个单词的映射
        Map<Character,String> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            // 如果记录过这个模式
            if (map.containsKey(pattern.charAt(i))){
//                if (!map.containsValue(s[i])){
                if (!map.get(pattern.charAt(i)).equals(s[i])){
                    // 但是没记录单词与记录过的单词不匹配
                    return false;
                }
            }else {
                // 模式未记录
                if (!map.containsValue(s[i])){
                    // 单词也未记录
                    // 记录新映射
                    map.put(pattern.charAt(i),s[i]);
                }else {
                    // 单词记录了
                    return false;
                }

            }
        }
        return true;

    }

    public static void main(String[] args) {
        System.out.println(new Solution().wordPattern("abba","dog cat cat dog"));
    }

}
