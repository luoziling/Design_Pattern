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
 * 按位与
 */
public class Solution1 {
    List<List<Integer>> result = new LinkedList<>();
    public List<List<Integer>> subsets(int[] nums) {
        // 判定例外情况
        if (nums.length == 0){
            return result;
        }
        // 一个数组有n个数字那么就有2的n次方个子集（包括空集与全集
        int allSet = 1<<nums.length;

        // 遍历添加
        // 一共要添加2的n次方个数组
        for (int i = 0; i < allSet; i++) {
            // 存放每个子集的数组记得每次都要new
            List<Integer> item = new LinkedList<>();
            // 每次遍历数组进行添加
            for (int j = 0; j < nums.length; j++) {
//                if ((i&(1<<j)) == 1){
//                    item.add(nums[j]);
//                }
                // 重点中的重点，需要仔细复习Java中的位运算法
                if ((1&(i>>j)) == 1){
                    item.add(nums[j]);
                }
            }
            result.add(item);
        }
        return result;
    }



    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};
        System.out.println(new Solution1().subsets(nums));
    }

}
