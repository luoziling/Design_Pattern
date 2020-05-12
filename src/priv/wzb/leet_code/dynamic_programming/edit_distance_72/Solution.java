package priv.wzb.leet_code.dynamic_programming.edit_distance_72;

/**
 * @author Satsuki
 * @time 2020/4/6 18:24
 * @description:
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 *
 * 你可以对一个单词进行如下三种操作：
 *
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 *  
 *
 * 示例 1：
 *
 * 输入：word1 = "horse", word2 = "ros"
 * 输出：3
 * 解释：
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 * 示例 2：
 *
 * 输入：word1 = "intention", word2 = "execution"
 * 输出：5
 * 解释：
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/edit-distance
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 动态规划
 *  * 原状态：一个单词转化为另一个单词的最少步骤，
 *  子状态：假设单词A有i个单词B有j个单词不断将单词数量缩小-1
 *  * 边界值:对于边界情况，一个空串和一个非空串的编辑距离为 D[i][0] = i 和 D[0][j] = j，D[i][0] 相当于对 word1 执行 i 次删除操作，D[0][j] 相当于对 word1执行 j 次插入操作。
 *
 *  * 状态转换方程:
 *  A与B最后一个字符相同
 *  D[i][j]=min(D[i][j-1]+1,D[i-1][j]+1,D[i-1][j-1])=1+min(D[i][j-1],D[i-1][j],D[i-1][j-1]-1)
 *  A与B最后一个字符不同：
 *  D[i][j] = 1+min(D[i][j-1],D[i-1][j],D[i-1][j-1])
 */
public class Solution {
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // 有一个字符为空
        if (n*m==0){
            return n+m;
        }
        // DP数组
        int[][] dp = new int[n+1][m+1];
        // 边界状态初始化
        for (int i = 0; i < n + 1; i++) {
            // 第0行要转为另一个单词（此时另一个单词为0个字符要修改i次）
            dp[i][0] = i;
        }
        for (int j = 0; j < m + 1; j++) {
            // 第0列相同
            dp[0][j] = j;
        }

        // 计算所有dp
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                int left = dp[i-1][j]+1;
                int down = dp[i][j-1]+1;
                int leftDown = dp[i-1][j-1];
                if (word1.charAt(i-1)!=word2.charAt(j-1)){
                    // 子字符串的最后一个字符不同则要多调整一次
                    leftDown+=1;
                }
                dp[i][j] = Math.min(left,Math.min(down,leftDown));
            }
        }
        return dp[n][m];
    }
}
