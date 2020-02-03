package priv.wzb.leet_code.graph_search.matchsticks_to_square_473;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Satsuki
 * @time 2019/12/1 17:10
 * @description:
 * 还记得童话《卖火柴的小女孩》吗？现在，你知道小女孩有多少根火柴，请找出一种能使用所有火柴拼成一个正方形的方法。不能折断火柴，可以把火柴连接起来，并且每根火柴都要用到。
 *
 * 输入为小女孩拥有火柴的数目，每根火柴用其长度表示。输出即为是否能用所有的火柴拼成正方形。
 *
 * 示例 1:
 *
 * 输入: [1,1,2,2,2]
 * 输出: true
 *
 * 解释: 能拼成一个边长为2的正方形，每边两根火柴。
 * 示例 2:
 *
 * 输入: [3,3,3,3,4]
 * 输出: false
 *
 * 解释: 不能用所有火柴拼成一个正方形。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/matchsticks-to-square
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 利用递归，深搜的概念尝试将火柴放入四个不同的桶中
 * 如果火柴总长度%4（对4取余）不为零就说明无法构成正方形直接返回false
 * 在放入前对数组排序从大到小，先把长的火柴放入桶中减少尝试次数
 * 在放入桶的时候桶中火柴总厂肯定得小于火柴总长度的四分之一
 *
 */
public class Solution {
    public boolean makesquare(int[] nums) {
        // 不构成四条边
        if (nums.length<4){
            return false;
        }
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        // 如果火柴总长度%4（对4取余）不为零就说明无法构成正方形直接返回false
        if (sum%4!=0){
            return false;
        }
        // 从大到小排序
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        };
        // 转为封装数组
        Integer[] nums1 = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            nums1[i] = nums[i];
        }

        Arrays.sort(nums1, Collections.reverseOrder());
        for (int i = 0; i < nums1.length; i++) {
            System.out.println(nums1[i]);
        }
        return generate(0,nums1,sum/4,new int[4]);
//        return false;

    }

    boolean generate(int i,Integer[] nums,int target,int[] bucket){
        if (i>=nums.length){
            return bucket[0] == target&&bucket[1]==target&&bucket[2] == target&&bucket[3]==target;
        }

        for (int j = 0; j < 4; j++) {
            if (bucket[j]+nums[i]>target){
                continue;
            }
            bucket[j] += nums[i];
            if (generate(i+1,nums,target,bucket)){
                return true;
            }
            bucket[j] -= nums[i];
        }
        return false;
    }

    public static void main(String[] args) {
//        new Solution().makesquare(new int[]{0,4,5,1,12,423,1,2});
        System.out.println(new Solution().makesquare(new int[]{1,1,2,2,2}));
    }
}
