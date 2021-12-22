package priv.wzb.leet_code_problems.leetcode.editor.cn;
//输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。
//
// 示例 1:
//
// 输入: n = 1
//输出: [1,2,3,4,5,6,7,8,9]
//
//
//
//
// 说明：
//
//
// 用返回一个整数列表来代替打印
// n 为正整数
//
// Related Topics 数组 数学 👍 163 👎 0

class DaYinCong1daoZuiDaDeNweiShuLcof{
	public static void main(String[] args) {
		Solution solution = new DaYinCong1daoZuiDaDeNweiShuLcof().new Solution();

	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] printNumbers(int n) {
    	int length = (int) Math.pow(10,n);
    	int[] res = new int[length-1];
		for (int i = 1; i < length; i++) {
			res[i-1] = i;
		}
		return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}
