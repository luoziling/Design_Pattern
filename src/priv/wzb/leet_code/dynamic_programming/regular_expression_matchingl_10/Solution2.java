package priv.wzb.leet_code.dynamic_programming.regular_expression_matchingl_10;

/**
 * @author Satsuki
 * @time 2020/1/19 22:35
 * @description:
 * 因为题目拥有最优子结构，一个自然的想法就是将中间结果保存起来。
 * 我们通过用 \text{dp(i,j)}dp(i,j) 表示 \text{text[i:]}text[i:]
 * 和 \text{pattern[j:]}pattern[j:] 是否能匹配。
 * 我们可以用更短的字符串匹配问题来表示原本的问题。
 *
 *
 *
 * 作者：LeetCode
 * 链接：https://leetcode-cn.com/problems/regular-expression-matching/solution/zheng-ze-biao-da-shi-pi-pei-by-leetcode/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * 动态规划
 * 自动向下
 */
public class Solution2 {
    // 帮助记忆
    // 记录中间过程
    Result[][] memo;

    public boolean isMatch(String text, String pattern) {
        // 初始化memo
        // +1的长度
        // x轴代表了text
        // y轴代表了pattern
        memo = new Result[text.length()+1][pattern.length()+1];
        return dp(0,0,text,pattern);


    }

    /**
     *
     * @param i 指向字符串text中的某个位置，之前的应该属于匹配过了
     * @param j 指向匹配pattern的某个位置
     * @param text 带匹配的字符串
     * @param pattern 匹配模式（类似正则表达式
     * @return 是否匹配
     * 由于有了ij的存在所以避免了使用subString 减少了字符串创建子串的时间
     * 并且使用memo进行中间结果的保存
     */
    public boolean dp(int i,int j,String text,String pattern){
        //我们通过用 \text{dp(i,j)}dp(i,j) 表示 \text{text[i:]}text[i:]
        // 和 \text{pattern[j:]}pattern[j:] 是否能匹配。
        // 我们可以用更短的字符串匹配问题来表示原本的问题。

        if (memo[i][j] != null){
            // 如果记录过/判断过则直接返回判断结果
            return memo[i][j] == Result.TRUE;
        }
        boolean ans;

        // 如果J指向pattern的最后一个
        // 也就是匹配pattern已经全部匹配完毕
        if (j == pattern.length()){
            // 若此时text也到达了最后也匹配完毕，说明字符串text符合pattern
            // 那么答案就是ans就是true
            // 否则如果pattern已经匹配完毕但是text却未完全匹配说明匹配失败，结果就是false
            ans = i == text.length();

        }else {
            // 第一个字符是否匹配
            boolean firstMatch = (i<text.length() && (pattern.charAt(j) == text.charAt(i) || pattern.charAt(j)=='.'));

            // 当未匹配完毕且下一个字符是*号的情况
            if (j+1 < pattern.length() && pattern.charAt(j+1) == '*'){
                // 结果分为两种情况
                // 第一种情况是*号前面的字符并未在text的前面出现过，由于*可以代表0个匹配所以可以直接跳过两个匹配pattern字符
                // 第二种情况就是*号前面的字符在text中出现了
                // 那么就让指向text的指针i后移与pattern的*号前面的字符继续匹配判断是否匹配
                ans = (dp(i,j+2,text,pattern)|| firstMatch && dp(i+1,j,text,pattern));

            }else {
                // 否则就说明是单纯的字符相等的匹配继续匹配下一个字符即可
                ans = firstMatch && dp(i+1,j+1,text,pattern);
            }

        }
        // 记忆化，优化记录优化时间效率
        memo[i][j] = ans?Result.TRUE:Result.FALSE;
        return ans;
    }
}
