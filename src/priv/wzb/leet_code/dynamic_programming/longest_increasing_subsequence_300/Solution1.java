package priv.wzb.leet_code.dynamic_programming.longest_increasing_subsequence_300;

/**
 * @author Satsuki
 * @time 2019/12/3 21:04
 * @description:
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 *
 * 示例:
 *
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 * 说明:
 *
 * 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
 * 你算法的时间复杂度应该为 O(n2) 。
 * 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 暴力法
 * 算法：
 * 最简单的方法是找到所有增加的子序列，然后返回最长增加的子序列的最大长度。为了做到这一点，我们使用递归函数 \text length of lislengthoflis 返回从当前元素（对应于 curposcurpos）开始可能的 lis 长度（包括当前元素）。在每个函数调用中，我们考虑两种情况：
 *
 * 当前元素大于包含在 lis 中的前一个元素。在这种情况下，我们可以在 lis 中包含当前元素。因此，我们通过将其包含在内，得出了 lis 的长度。此外，我们还通过不在 lis 中包含当前元素来找出 lis 的长度。因此，当前函数调用返回的值是两个长度中的最大值。
 * 当前元素小于包含在 lis 中的前一个元素。在这种情况下，我们不能在 lis 中包含当前元素。因此，我们只通过不在 lis 中包含当前元素（由当前函数调用返回）来确定 lis 的长度。
 *
 */
public class Solution1 {
    public int lengthOfLIS(int[] nums) {
        return lengthOfLIS(nums,Integer.MIN_VALUE,0);
    }

    /**
     *
     * @param nums 原始数组
     * @param prev 前面元素中最大的
     * @param curpos 数组下标
     * @return
     */
    public int lengthOfLIS(int[] nums,int prev,int curpos){
        // 程序出口之一 ：遍历完了整个数组
        if (curpos == nums.length){
            return 0;
        }
        // 面对一个元素我们有两种做法，获取或者不获取
        int taken = 0;
        // 如果这个元素大于前面元素中的最大元素
        if (nums[curpos]>prev){
            // 获取当前元素
            taken = 1+lengthOfLIS(nums,nums[curpos],curpos+1);
        }
        // 进行不获取当前元素的尝试
        int nottaken = lengthOfLIS(nums,prev,curpos+1);

        return Math.max(taken,nottaken);

    }
}
