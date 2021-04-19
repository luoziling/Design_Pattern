package priv.wzb.leet_code.array.offer_03;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-07 20:43
 * @description:
 **/

public class Solution {
	public int findRepeatNumber(int[] nums) {
		Map<Integer,Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			if (map.get(nums[i]) != null){
				return nums[i];
			}
			map.put(nums[i],map.getOrDefault(nums[i],0)+1);
		}
		return -1;
	}
}
