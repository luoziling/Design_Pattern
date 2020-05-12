package priv.wzb.leet_code.tree_and_graph.short_encoding_of_words_820;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Satsuki
 * @time 2020/3/28 18:31
 * @description:
 * 给定一个单词列表，我们将这个列表编码成一个索引字符串 S 与一个索引列表 A。
 *
 * 例如，如果这个列表是 ["time", "me", "bell"]，我们就可以将其表示为 S = "time#bell#" 和 indexes = [0, 2, 5]。
 *
 * 对于每一个索引，我们可以通过从字符串 S 中索引的位置开始读取字符串，直到 "#" 结束，来恢复我们之前的单词列表。
 *
 * 那么成功对给定单词列表进行编码的最小字符串长度是多少呢？
 *
 *  
 *
 * 示例：
 *
 * 输入: words = ["time", "me", "bell"]
 * 输出: 10
 * 说明: S = "time#bell#" ， indexes = [0, 2, 5] 。
 *  
 *
 * 提示：
 *
 * 1 <= words.length <= 2000
 * 1 <= words[i].length <= 7
 * 每个单词都是小写字母 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/short-encoding-of-words
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int minimumLengthEncoding(String[] words) {
        // 首先利用Set的特性去重
        Set<String> good = new HashSet<>(Arrays.asList(words));
        for(String word: words){
            // 根据限定知道每个单词最多七个后缀
            // 从第二个开始遍历到最后发现有后缀就删除
            for (int i = 1; i < word.length(); i++) {
                good.remove(word.substring(i));
            }
        }
        // 到这里set就只剩下无重复并且无法压缩的其余单词
        int ans = 0;
        // 构建压缩列表统计字符数
        for (String word: good){
            ans+= word.length()+1;
        }
        return ans;
    }
}
