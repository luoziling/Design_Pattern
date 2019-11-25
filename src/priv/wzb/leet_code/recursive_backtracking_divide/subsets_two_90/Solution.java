package priv.wzb.leet_code.recursive_backtracking_divide.subsets_two_90;

import priv.wzb.interview.test.P;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/20 13:20
 * @description:
 * 先排序再使用set保存有序
 */
public class Solution {
    List<List<Integer>> result = new LinkedList<>();
    HashSet<List<Integer>> set = new HashSet<>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // 特殊情况
        if (nums.length == 0){
            return result;
        }

        List<List<Integer>> mResult = new LinkedList<>();

        List<Integer> item = new LinkedList<>();
        generate(0,nums,item);
        result.add(new LinkedList<>());

        // 排序去重
        for(List a:result){
            Collections.sort(a);
        }


        for(List a: result) {
            set.add(a);
        }
        Iterator<List<Integer>> iterator = set.iterator();
        // 清空
//        result = null;


        // 重新添加
        while (iterator.hasNext()){
            mResult.add(iterator.next());
        }

        return mResult;
    }

    public void generate(int i,int[] nums,List<Integer> item){
        // 结束条件
        if (i>=nums.length){
            return;
        }

        item.add(nums[i]);
        result.add(new LinkedList<>(item));
        generate(i+1,nums,item);
        item.remove(item.size()-1);
        generate(i+1,nums,item);


    }

    public static void main(String[] args) {
//        int[] nums = new int[]{1,2,3};
        int[] nums = new int[]{1,2,2};
        System.out.println(new Solution().subsetsWithDup(nums));
    }

}
