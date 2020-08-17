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
 */
public class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int s1Len = s1.length();
        int s2Len = s2.length();
        int s3Len = s3.length();

        for (int i = 0; i < s3Len; i++) {
            if (!s3.substring(i,s1Len).equals(s1)){
                if (!s3.substring(i,s2Len).equals(s2)){
                    // 当 当前字串不符合任何一个s1或者s2就直接返回false
                    return false;
                }else {
                    // 满足s2 移动i
                    i+=s2Len-1;

                }
            }else {
                i+=s1Len-1;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbcbcac";
        System.out.println(new Solution().isInterleave(s1,s2,s3));
    }
}
