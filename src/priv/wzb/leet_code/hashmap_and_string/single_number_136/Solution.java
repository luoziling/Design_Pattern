package priv.wzb.leet_code.hashmap_and_string.single_number_136;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Satsuki
 * @time 2020/5/14 0:04
 * @description:
 */
public class Solution {
    public int singleNumber(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.get(nums[i]) == null){
                map.put(nums[i],1);
            }else {
                map.put(nums[i],map.get(nums[i])+1);
            }
        }
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            if (entry.getValue()==1){
                return entry.getKey();
            }
        }
        return -1;
    }
}
