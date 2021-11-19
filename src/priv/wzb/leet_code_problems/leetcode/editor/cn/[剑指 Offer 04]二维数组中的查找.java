package priv.wzb.leet_code_problems.leetcode.editor.cn;
//在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个
//整数，判断数组中是否含有该整数。
//
//
//
// 示例:
//
// 现有矩阵 matrix 如下：
//
//
//[
//  [1,   4,  7, 11, 15],
//  [2,   5,  8, 12, 19],
//  [3,   6,  9, 16, 22],
//  [10, 13, 14, 17, 24],
//  [18, 21, 23, 26, 30]
//]
//
//
// 给定 target = 5，返回 true。
//
// 给定 target = 20，返回 false。
//
//
//
// 限制：
//
// 0 <= n <= 1000
//
// 0 <= m <= 1000
//
//
//
// 注意：本题与主站 240 题相同：https://leetcode-cn.com/problems/search-a-2d-matrix-ii/
// Related Topics 数组 二分查找 分治 矩阵 👍 492 👎 0

class ErWeiShuZuZhongDeChaZhaoLcof{
	public static void main(String[] args) {
		Solution solution = new ErWeiShuZuZhongDeChaZhaoLcof().new Solution();
		int[] arr = {1,4,7,11,15};
		solution.binarySearch(arr,4);

	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
    	for(int[] arr:matrix){
    		// 举证遍历+二分查找
			boolean searchRes = binarySearch(arr, target);
			if (searchRes){
				return true;
			}
		}
    	return false;
    }
    boolean binarySearch(int[] arr,int target){
    	// 二分查找注意点：注意覆盖所有情况
    	int start = 0,end = arr.length-1;
    	while (start<=end){
    		int mid = start + ((end-start) >> 1);
    		if (arr[mid] == target){
    			return true;
			}
    		if (arr[mid]>=target){
    			end = mid-1;
			}else {
    			start = mid+1;
			}
		}
    	return false;
	}
}
//leetcode submit region end(Prohibit modification and deletion)

}
