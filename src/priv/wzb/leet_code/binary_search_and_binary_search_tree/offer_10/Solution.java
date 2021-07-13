package priv.wzb.leet_code.binary_search_and_binary_search_tree.offer_10;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-21 21:00
 * @description:
 **/

public class Solution {
	public int minArray(int[] numbers) {
		// 暴力法，不管有序数组是截取前面的放到后面还是截取后面的放到前面
		// 总会出现这种情况：从头开始遍历，肯定是有序，若遇到下一个小于当前的则就遇到了分界点返回即可
		int minIndex = 0;
		for (int i = 0; i < numbers.length; i++) {
			if ((i+1) < numbers.length && numbers[i] > numbers[i+1]){
				minIndex = i;
			}
		}
		return numbers[minIndex];
	}
}
