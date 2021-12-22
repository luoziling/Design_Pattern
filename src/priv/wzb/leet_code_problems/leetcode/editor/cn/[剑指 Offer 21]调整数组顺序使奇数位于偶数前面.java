package priv.wzb.leet_code_problems.leetcode.editor.cn;
//输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数在数组的前半部分，所有偶数在数组的后半部分。
//
//
//
// 示例：
//
//
//输入：nums = [1,2,3,4]
//输出：[1,3,2,4]
//注：[3,1,2,4] 也是正确的答案之一。
//
//
//
// 提示：
//
//
// 0 <= nums.length <= 50000
// 0 <= nums[i] <= 10000
//
// Related Topics 数组 双指针 排序 👍 173 👎 0

class DiaoZhengShuZuShunXuShiQiShuWeiYuOuShuQianMianLcof{
	public static void main(String[] args) {
		Solution solution = new DiaoZhengShuZuShunXuShiQiShuWeiYuOuShuQianMianLcof().new Solution();

	}
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] exchange(int[] nums) {
    	int oddIndex = 0;
    	int evenIndex = nums.length-1;
    	int[] res = new int[nums.length];
    	for(int i = 0;i<nums.length;i++){
    		if (nums[i] % 2 ==0){
    			res[evenIndex--] = nums[i];
//    			swap(nums,i,evenIndex);
//    			evenIndex--;
			}else {
				res[oddIndex++] = nums[i];
//    			swap(nums,i,oddIndex);
//    			oddIndex++;
			}
		}
    	return res;

    }
    public void swap(int[] nums,int i,int j){
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}
}
//leetcode submit region end(Prohibit modification and deletion)

}
