package priv.wzb.leet_code_problems.leetcode.editor.cn;
//把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2
//] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。
//
// 示例 1：
//
// 输入：[3,4,5,1,2]
//输出：1
//
//
// 示例 2：
//
// 输入：[2,2,2,0,1]
//输出：0
//
//
// 注意：本题与主站 154 题相同：https://leetcode-cn.com/problems/find-minimum-in-rotated-
//sorted-array-ii/
// Related Topics 数组 二分查找 👍 446 👎 0

class XuanZhuanShuZuDeZuiXiaoShuZiLcof{
	public static void main(String[] args) {
		Solution solution = new XuanZhuanShuZuDeZuiXiaoShuZiLcof().new Solution();
		int[] arr = {1,3,5};
//		int[] arr = {3,4,5,1,2};
		System.out.println("solution.minArray(arr) = " + solution.minArray(arr));

	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
	/**
	 * 思路：二分查找 查找部分有序的
	 * 找最小值，替换为找翻转后的第一个节点
	 * 遇到相同的略过
	 * @param numbers
	 * @return
	 */
    public int minArray(int[] numbers) {
    	// 由于旋转， 最大点的下一个就是旋转的头部 也就是最小 找到旋转点（最大
		// 关键在于要找什么
		int start = 0,end = numbers.length-1;
		// 相等说明找到了 目标是找最小数 最小数肯定在反转后的第一个
		// 目标变为找翻转后的第一个节点
		while (start<end){
			int mid = start+((end-start)>>1);
			if (numbers[mid]>numbers[end]){
				// 中点比最后大 说明旋转后数组拼接到右侧
				start = mid+1;
			}else if (numbers[mid]<numbers[end]){
				// mid也可能是最小的 因此不能诶筛选
				end = mid;
			}else {
				// 存在相等情况忽略最后一个继续判断
				end--;
			}
		}

    	return numbers[start];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

	public int minArray1(int[] numbers) {
		// 二分法
		// middle = (min+max) /2
		// 一般条件：顺序存储 元素有序
		// 原因：下标就可得到关键字 关键字可确定前后
		// 数组在一定程度上有规律，可用二分思路区分left right 当前指向的mid
		// 如果left > mid 说明前面无序，要找的就在前面
		// right<mid说明后面无须，要找的在后面
		// 目标:找最小数
		// 方案：通过对比缩小范围
		int left = 0,right = numbers.length-1;
		if (right == 0){
			return numbers[0];
		}
		while (left<right){
			int mid = left + (right-left)/2;
			if (numbers[right] > numbers[mid]){
				// 右侧正常递增则最小在左侧
				right = mid;
			}else if (numbers[right] < numbers[mid]){
				// 否则在右侧
				left = mid +1;
			}else{
				// 特殊情况 多个重复数字
				// 此时缩小返回
				right--;
			}
		}
		return numbers[left];
	}

}
