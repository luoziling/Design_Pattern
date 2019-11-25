package priv.wzb.leet_code.greedy.wiggle_subsequence_376;

/**
 * @author Satsuki
 * @time 2019/11/14 20:42
 * @description:
 * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可能是正数或负数。少于两个元素的序列也是摆动序列。
 *
 * 例如， [1,7,4,9,2,5] 是一个摆动序列，因为差值 (6,-3,5,-7,3) 是正负交替出现的。相反, [1,4,7,2,5] 和 [1,7,4,5,5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
 *
 * 给定一个整数序列，返回作为摆动序列的最长子序列的长度。 通过从原始序列中删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原始顺序。
 *
 * 示例 1:
 *
 * 输入: [1,7,4,9,2,5]
 * 输出: 6
 * 解释: 整个序列均为摆动序列。
 * 示例 2:
 *
 * 输入: [1,17,5,10,13,15,10,5,16,8]
 * 输出: 7
 * 解释: 这个序列包含几个长度为 7 摆动序列，其中一个可为[1,17,10,13,10,16,8]。
 * 示例 3:
 *
 * 输入: [1,2,3,4,5,6,7,8,9]
 * 输出: 2
 * 进阶:
 * 你能否用 O(n) 时间复杂度完成此题?
 *
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/wiggle-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int wiggleMaxLength(int[] nums) {
        // 小于2直接为摇摆序列
        if (nums.length<2)
            return nums.length;
        if (nums.length==2){
            if (nums[0]==nums[1]){
                // 此时不是摇摆序列
                return 0;
            }else {
                return 2;
            }
        }
        // 从第一个开始
        int max =1;
        for (int i = 1; i < nums.length; i++) {
            if (i == nums.length-1){
                // 最后一个
                if (nums[i]-nums[i-1]>0&&nums[i-1]-nums[i-2]<0){
                    max++;
                }else if (nums[i-1]-nums[i-2]<0&&nums[i]-nums[i-1]>0){
                    max++;
                }
            }else {
                if (nums[i]-nums[i-1]>0&&nums[i+1]-nums[i]<0){
                    max++;
                }else if (nums[i]-nums[i-1]<0&&nums[i+1]-nums[i]>0){
                    max++;
                }
            }

        }
        return max;
    }
}
