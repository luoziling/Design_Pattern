package priv.wzb.leet_code.dynamic_programming.interleaving_string_97;

/**
 * @author Satsuki
 * @time 2020/7/18 14:55
 * @description:
 * 给定三个字符串 s1, s2, s3, 验证 s3 是否是由 s1 和 s2 交错组成的。
 *
 * 示例 1:
 *
 * 输入: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 * 输出: true
 * 示例 2:
 *
 * 输入: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 * 输出: false
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/interleaving-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 动态规划
 * 边界：s3（0） = f(0,0) = true
 * f(i,j)=s3(p)表示由s1的i个元素和s2的j个元素组成的复合字符串是否与s3的前p个元素相等
 * p=i+j-1
 * 状态转移方程：f(i,j)=[f(i−1,j)ands1(i−1)=s3(p)]or[f(i,j-1)ands2(j−1)=s3(p)]
 */
public class Solution1 {
    public boolean isInterleave(String s1, String s2, String s3) {
        int s1Len = s1.length();
        int s2Len = s2.length();
        int s3Len = s3.length();
        if (s1Len + s2Len != s3Len) {
            return false;
        }

        // 初始化数组默认全为false
        boolean[][] dp = new boolean[s1Len + 1][s2Len + 1];

        dp[0][0] = true;

        for (int i = 0; i <= s1Len; ++i) {
            for (int j = 0; j <= s2Len; ++j) {
                int p = i + j - 1;
                // 可看作整个dp数组从左上移动到右下，右下就是答案，代表了遍历完整个s3数组
                if (i > 0) {
                    // 右移了
                    // 向右探索一步，添加一个s1的字符看看是否符合s3在该位置的值
                    // 前提得是探索之前是true说明前面的组成的临时s3字串与s3中对应的字符相同
                    dp[i][j] = dp[i][j] || (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(p));
                }
                if (j > 0) {
                    // 向下移动
                    // 向下探索一步，添加一个s2的字符看看是否符合s3在该位置的值
                    // 前提得是探索之前是true说明前面的组成的临时s3字串与s3中对应的字符相同
                    dp[i][j] = dp[i][j] || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }

        }
        return dp[s1Len][s2Len];
    }

    public static void main(String[] args) {
//        String s1 = "aabcc";
//        String s2 = "dbbca";
//        String s3 = "aadbbcbcac";
//        System.out.println(new Solution1().isInterleave(s1,s2,s3));
        boolean[] s1 = new boolean[6];
        for (int i = 0; i < s1.length; i++) {
            System.out.println(s1[i]);
        }
    }
}
