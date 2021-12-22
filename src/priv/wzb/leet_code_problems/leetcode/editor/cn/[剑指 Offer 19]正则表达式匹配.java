package priv.wzb.leet_code_problems.leetcode.editor.cn;
//请实现一个函数用来匹配包含'. '和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（含0次）。在本题中，匹配
//是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但与"aa.a"和"ab*a"均不匹配。
//
// 示例 1:
//
// 输入:
//s = "aa"
//p = "a"
//输出: false
//解释: "a" 无法匹配 "aa" 整个字符串。
//
//
// 示例 2:
//
// 输入:
//s = "aa"
//p = "a*"
//输出: true
//解释: 因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
//
//
// 示例 3:
//
// 输入:
//s = "ab"
//p = ".*"
//输出: true
//解释: ".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
//
//
// 示例 4:
//
// 输入:
//s = "aab"
//p = "c*a*b"
//输出: true
//解释: 因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
//
//
// 示例 5:
//
// 输入:
//s = "mississippi"
//p = "mis*is*p*."
//输出: false
//
//
// s 可能为空，且只包含从 a-z 的小写字母。
// p 可能为空，且只包含从 a-z 的小写字母以及字符 . 和 *，无连续的 '*'。
//
//
// 注意：本题与主站 10 题相同：https://leetcode-cn.com/problems/regular-expression-matching/
//
// Related Topics 递归 字符串 动态规划 👍 301 👎 0

class ZhengZeBiaoDaShiPiPeiLcof{
	public static void main(String[] args) {
		Solution solution = new ZhengZeBiaoDaShiPiPeiLcof().new Solution();
		String s = "mississippi";
		String p = "mis*is*p*.";
		System.out.println("solution.isMatch(s,p) = " + solution.isMatch(s, p));
	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isMatch(String s, String p) {
		int sLength = s.length();
		int pLength = p.length();
		int i =0,j = 0;
		while (i<sLength && j<pLength){
			// 特殊情况遇到 .*
			if (p.charAt(j) == '.' || p.charAt(j) == s.charAt(i)){
				if (p.charAt(j) == '.' && (j+1)<pLength && p.charAt(j+1) == '*' && (j+2==pLength)){
					i = sLength;
					j = pLength;
					break;
				}
				if (j+2<pLength){
					break;
				}
				i++;
				j++;
			}else if (p.charAt(j) == '*'){
				// *则判断之前一个是否相等
				if (p.charAt(j-1) == s.charAt(i)){
					// 处理j*是最后一个的情况
					i++;
					if (j == pLength-1){
						j++;
						break;
					}
				}else {
					j++;
				}
			}else {
				// 否则不相等
				// 注意判断带*的不相等情况
				if ((j+1) < pLength && p.charAt(j+1) == '*'){
					// x*且x步匹配则跳过
					j+=2;
				}else {
					break;
				}
			}
		}
		return (i == sLength) && (j == pLength);
	}
}
//leetcode submit region end(Prohibit modification and deletion)

}
