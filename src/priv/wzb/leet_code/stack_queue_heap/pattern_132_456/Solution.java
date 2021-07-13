package priv.wzb.leet_code.stack_queue_heap.pattern_132_456;

import java.util.TreeMap;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-03-24 21:02
 * @description:
 **/

public class Solution {
	public boolean find132pattern(int[] nums) {
		int n = nums.length;
		if (n<3){
			return false;
		}
		// 左侧最小
		int leftMin = nums[0];
		// 右侧所有元素
		TreeMap<Integer,Integer> rightAll = new TreeMap<>();
		for (int k = 2; k < n; k++) {
			rightAll.put(nums[k],rightAll.getOrDefault(nums[k],0) + 1);
		}

		for (int j = 1; j < n - 1; j++) {
			if (leftMin < nums[j]){
				Integer next = rightAll.ceilingKey(leftMin + 1);
				// 在左侧找到最小值和次小值
				// 维护左侧最小值，默认当前为最大值，找到次小值则说明匹配成功并返回
				if (next!=null && next<nums[j]){
					return true;
				}
			}
			// 遍历更新左侧最小值
			leftMin = Math.min(leftMin,nums[j]);
			// 如果遍历过程中不断向右推移，遇到全部判断过的就移除treeMap
			rightAll.put(nums[j+1],rightAll.get(nums[j+1])-1);
			if (rightAll.get(nums[j+1])==0){
				rightAll.remove(nums[j+1]);
			}
		}
		return false;
	}
}
