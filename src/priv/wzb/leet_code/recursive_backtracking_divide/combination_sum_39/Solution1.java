package priv.wzb.leet_code.recursive_backtracking_divide.combination_sum_39;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/20 15:54
 * @description:
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 *
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 *
 * 输入: candidates = [2,3,6,7], target = 7,
 * 所求解集为:
 * [
 *   [7],
 *   [2,2,3]
 * ]
 * 示例 2:
 *
 * 输入: candidates = [2,3,5], target = 8,
 * 所求解集为:
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution1 {
    List<List<Integer>> result = new LinkedList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> item = new LinkedList<>();
        // 排序
        Arrays.sort(candidates);
        // 生成结果
        generate(0,candidates,item,0,target);
        return result;
    }

    public void generate(int i,int[] nums,List<Integer> item,int sum,int target){
        // 返回条件，数组越界或者子集和超过目标和
        if (i>=nums.length||sum>target){
            return;
        }

        // 累加
        sum+= nums[i];
        item.add(nums[i]);
        // 判定子集是否合格
        // 因为测试集合是无重复的所以不需要set判定是否重复
        if (sum == target){
            result.add(new LinkedList<>(item));
        }
        // 因为是可重复，这边使用i而不是i+1
        generate(i,nums,item,sum,target);
        sum-=nums[i];
        item.remove(item.size()-1);
        generate(i+1,nums,item,sum,target);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,2};
        System.out.println(new Solution1().combinationSum(nums,7));
    }
}
