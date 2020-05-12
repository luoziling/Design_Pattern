package priv.wzb.leet_code.array.sort_an_array_912;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Satsuki
 * @time 2020/3/31 17:20
 * @description:
 * 给你一个整数数组 nums，请你将该数组升序排列。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [5,2,3,1]
 * 输出：[1,2,3,5]
 * 示例 2：
 *
 * 输入：nums = [5,1,1,2,0,0]
 * 输出：[0,0,1,1,2,5]
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 50000
 * -50000 <= nums[i] <= 50000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sort-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public List<Integer> sortArray(int[] nums) {
        Arrays.sort(nums);
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            array.add(nums[i]);
        }

        return  array;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().sortArray(new int[]{5,2,3,1}));
    }


}
