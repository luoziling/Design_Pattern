package priv.wzb.leet_code.dynamic_programming.longest_increasing_subsequence_300;

import java.util.Arrays;

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
 * 带记忆的递归
 * 使用二维数组memo保存之前出现过的结果
 * 在前面的方法中，许多递归调用必须使用相同的参数进行一次又一次的调用。通过将为特定调用获得的结果存储在二维记忆数组 memomemo 中，可以消除这种冗余。memo[i][j]memo[i][j] 表示使用 nums[i]nums[i] 作为上一个被认为包含/不包含在 lis 中的元素的 lis 可能的长度，其中 nums[j]nums[j] 作为当前被认为包含/不包含在 lis 中的元素。这里，numsnums 表示给定的数组。
 *
 */
public class Solution2 {
    public int lengthOfLIS(int[] nums) {
        int memo[][] = new int[nums.length+1][nums.length];
        for(int[] l : memo){
            Arrays.fill(l,-1);
        }
        return lengthOfLIS(nums,-1,0,memo);
    }

    /**
     *
     * @param nums 原始数组
     * @param prev 前面元素中最大的
     * @param curpos 数组下标
     * @param memo 记忆化数组 meme[i][j] 代表了第i个元素的最长上升子序
     * @return
     */
    public int lengthOfLIS(int[] nums,int prev,int curpos,int[][] memo){
        // 程序出口之一 ：遍历完了整个数组
        if (curpos == nums.length){
            return 0;
        }
        // 如果之前记录过
        if (memo[prev+1][curpos]>=0){
            return memo[prev+1][curpos];
        }
        // 面对一个元素我们有两种做法，获取或者不获取
        int taken = 0;
        // 如果这个元素大于前面元素中的最大元素
        if (prev<0 || nums[curpos]>nums[prev]){
            // 获取当前元素
            taken = 1+lengthOfLIS(nums,curpos,curpos+1,memo);
        }
        // 进行不获取当前元素的尝试
        int nottaken = lengthOfLIS(nums,prev,curpos+1,memo);
        memo[prev+1][curpos] = Math.max(taken,nottaken);
        return memo[prev+1][curpos];

    }
}
