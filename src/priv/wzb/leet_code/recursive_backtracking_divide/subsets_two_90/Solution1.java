package priv.wzb.leet_code.recursive_backtracking_divide.subsets_two_90;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/20 13:20
 * @description:
 */
public class Solution1 {
    List<List<Integer>> result = new LinkedList<>();
    HashSet<List<Integer>> set = new HashSet<>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // 特殊情况
        if (nums.length == 0){
            return result;
        }
        // 对nums排序
        Arrays.sort(nums);

        List<List<Integer>> mResult = new LinkedList<>();

        List<Integer> item = new LinkedList<>();
        generate(0,nums,item);
        result.add(new LinkedList<>());



        return result;
    }

    public void generate(int i,int[] nums,List<Integer> item){
        // 结束条件
        if (i>=nums.length){
            return;
        }

        item.add(nums[i]);
        // 排序
        Collections.sort(item);
        // 去重
        if (!set.contains(item)){
            // 不包括重复才添加
            result.add(new LinkedList<>(item));
            set.add(new LinkedList<>(item));
        }
//        result.add(new LinkedList<>(item));
        generate(i+1,nums,item);
        item.remove(item.size()-1);
        generate(i+1,nums,item);


    }

    public static void main(String[] args) {
//        int[] nums = new int[]{1,2,3};
        int[] nums = new int[]{1,2,2};
        System.out.println(new Solution1().subsetsWithDup(nums));
    }

}
