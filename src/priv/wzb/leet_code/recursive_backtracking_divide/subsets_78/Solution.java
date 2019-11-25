package priv.wzb.leet_code.recursive_backtracking_divide.subsets_78;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/19 22:25
 * @description:
 * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 *
 * 说明：解集不能包含重复的子集。
 *
 * 示例:
 *
 * 输入: nums = [1,2,3]
 * 输出:
 * [
 *   [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subsets
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 思路
 * 回溯法
 */
public class Solution {
    List<List<Integer>> result = new LinkedList<>();
    public List<List<Integer>> subsets(int[] nums) {
//        List<List<Integer>> result = new LinkedList<>();
        // 判定例外情况
        if (nums.length == 0){
            return result;
        }
        List<Integer> item = new LinkedList<>();
        // 排序
//        Arrays.sort(nums);
        // 获取子集
        generate(0,nums,item);
        // 别忘记了将空集合添加
        result.add(new LinkedList<>());
        return result;
    }

    /**
     *
     * 作用就是将数组转化成一个个子集存入result
     * 与八皇后问题的解法类似，回溯
     * 每次都到达最底层的时候进行回溯
     * @param i i 代表着数组的下标也代表着item中要加入的值的下标
     * @param nums 原始数组
     * @param item 每一个子集
     *
     */
    public void generate(int i,int[] nums,List<Integer> item){
        // 递归结束条件
        if (i>=nums.length){
            return;
        }
        item.add(nums[i]);
        // 在Java中不能这样写，这样写的话result中添加的就是一个item对象的引用
        // item是在不停变化的，所以result中存储的东西也在不停变化
//        result.add(item);
        result.add(new LinkedList<>(item));
        generate(i+1,nums,item);
        // 移除最后一个进行递归
        item.remove(item.size()-1);
        generate(i+1,nums,item);

    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};
        System.out.println(new Solution().subsets(nums));
    }

}
