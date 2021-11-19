package priv.wzb.leet_code_problems.leetcode.editor.cn;
//请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
//
//
//
// 示例 1：
//
// 输入：s = "We are happy."
//输出："We%20are%20happy."
//
//
//
// 限制：
//
// 0 <= s 的长度 <= 10000
// Related Topics 字符串 👍 172 👎 0

class TiHuanKongGeLcof{
	public static void main(String[] args) {
		Solution solution = new TiHuanKongGeLcof().new Solution();

	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String replaceSpace(String s) {
    	char[] charArr = s.toCharArray();
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0;i<charArr.length;i++){
    		if(charArr[i] == ' '){
    			sb.append("%20");
			}else {
    			sb.append(charArr[i]);
			}
		}
    	return sb.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}
