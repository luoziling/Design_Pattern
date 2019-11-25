package priv.wzb.leet_code.twosum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/11/7 22:57
 * @description:
 * 两数之和
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 一开始的暴力匹配
 * 其实可以把这个问题转换为在一个数组中有个数字
 * 在数组剩下的数字中能否找到一个合适的数字使两个数字之和为target
 */
public class Solution1 {
    public static int[] twoSum(int[] nums, int target) {
        // 暴力匹配，效率低时间复杂度为O(n²)
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = i+1; j < nums.length; j++) {
////                if (nums[i] + nums[j] == target){
////                    return new int[]{i,j};
////                }
//                if (nums[j] == target-nums[i]){
//                    return new int[]{i,j};
//                }
//            }
//        }
        // 利用哈希表用空间换时间使时间复杂度变为O(n)但是空间复杂度会从O（1）变为O（n）
        // 普通数组遍历一次的时间复杂度为O(n)
        // 哈希表其实提供了一种判定是否包含某数的方法
        // 将原始数组转为HashMap
//        Map<Integer,Integer> map = new HashMap<>();
//        for (int i = 0; i < nums.length; i++) {
////            map.put(i,nums[i]);
//            // 最好使用key保存题目中的数组内容，防止重复
//            map.put(nums[i],i);
//        }
//        // 遍历数组并且在哈希表中找出是否存在另一个符合要求的数字得到两数之和为target
//        int another;
//        for (int i = 0; i < nums.length; i++) {
//            another = target - nums[i];
//            if (map.containsKey(another)&&map.get(another)!=i){
//                return new int[]{i,map.get(another)};
//            }
//        }

        //再次减少时间复杂度
        Map<Integer,Integer> map = new HashMap<>();
        int another;
        // 因为假设可以找到这两个数字之和为target那么这两个数字一定包含在nums中
        // i对数组遍历,用map对i之前的数字遍历[0，i）来查找是否存在这样两个数字
        for (int i = 0; i < nums.length; i++) {
            another = target - nums[i];
            if (map.containsKey(another)){
                return new int[]{map.get(another),i};
            }
            map.put(nums[i],i);
        }

        return null;
    }

    public static void main(String[] args) {
        // 我们来换更长的数组测试
        int [] arr = new int[100000];
        for (int i = 0; i < 100000; i++) {
            arr[i] = i;
        }
        long now = System.currentTimeMillis();
//        int[] ints = twoSum(new int[]{2, 7, 11, 15}, 9);
        int[] ints = twoSum(arr, 100001);
        long end = System.currentTimeMillis();
        System.out.println("花费时间：" + (end-now));
        // 14-16 hashmap单遍遍历（看似单遍
        // 16-19 hashmap双遍遍历
        // 5 初始暴力匹配
        // 4-5官方暴力匹配

        System.out.println("ints:" + Arrays.toString(ints));

    }
}
