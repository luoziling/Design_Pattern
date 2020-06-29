package priv.wzb.leet_code.hashmap_and_string.single_number_136;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2020/5/14 0:04
 * @description:
 */
public class Solution1 {
    public int singleNumber(int[] nums) {
        int ans=0;
        for(int i=0;i<nums.length;i++){
            ans^=nums[i];
        }
        return ans;
    }
}
