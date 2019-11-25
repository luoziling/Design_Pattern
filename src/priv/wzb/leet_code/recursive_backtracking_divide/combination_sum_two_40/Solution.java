package priv.wzb.leet_code.recursive_backtracking_divide.combination_sum_two_40;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/20 15:29
 * @description:
 * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的每个数字在每个组合中只能使用一次。
 *
 * 说明：
 *
 * 所有数字（包括目标数）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 *
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 所求解集为:
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 * 示例 2:
 *
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 所求解集为:
 * [
 *   [1,2,2],
 *   [5]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    // 保存结果
    List<List<Integer>> result = new LinkedList<>();
    // 保存中间过程的item因为是set所以可以判定是否有重复
    HashSet<List<Integer>> set = new HashSet<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<Integer> item = new LinkedList<>();
        // 排序，方便后续操作
        // 数组就是用arrays进行排序，如果是collection对象则用Collections进行排序
        Arrays.sort(candidates);
        // 生成结果
        generate(0,candidates,item,0,target);
        return result;
    }

    /**
     *
     * @param i 用于记录当前遍历到了数组的什么位置，也可以用于防止越界
     * @param nums 原始数组
     * @param item 中间结果的保存
     * @param sum 记录子集的累加和
     * @param target 目标累加和
     */
    public void generate(int i,int[] nums,List<Integer> item,int sum,int target){
        // 递归退出条件/回溯返回条件
        // 当元素递归遍历时到达原始数组末尾或者子集合大于目标累加和
        if (i>=nums.length|| sum>target){
            return;
        }
        // 累加
        sum += nums[i];
        // 添加到当前子集中
        item.add(nums[i]);
        // 判断是否是需要的子集
        // 当前子集累加和等于目标累加和并且无重复记录
        if (target == sum && !set.contains(item)){
            // 当前子集为条件限定的子集放入结果与set
            // 注意：每次存储必须new对象
            // 否则维持同一个对象的时候若对象更改那么会对原来结果产生影响
            result.add(new LinkedList<>(item));
            set.add(new LinkedList<>(item));
        }
        // 双重的递归代表着回溯
        generate(i+1,nums,item,sum,target);
        // 条件不正确选择树的另一条路进行尝试
        // 把刚刚的尝试剔除
        sum-=nums[i];
        item.remove(item.size()-1);
        // 回溯
        generate(i+1,nums,item,sum,target);
    }
}
