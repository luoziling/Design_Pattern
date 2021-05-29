package priv.wzb.leet_code.binary_search_and_binary_search_tree.offer_10;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-21 21:00
 * @description:
 **/

public class Solution1 {
	public int minArray(int[] numbers) {
		// 二分法
		// middle = (min+max) /2
		// 一般条件：顺序存储 元素有序
		// 原因：下标就可得到关键字 关键字可确定前后
		// 数组在一定程度上有规律，可用二分思路区分left right 当前指向的mid
		// 如果left > mid 说明前面无序，要找的就在前面
		// right<mid说明后面无须，要找的在后面
		int left = 0,right = numbers.length-1;
		if (right == 0){
			return numbers[0];
		}
		while (left<right){
			int mid = left + (right-left)/2;
			if (numbers[right] > numbers[mid]){
				right = mid;
			}else if (numbers[right] < numbers[mid]){
				left = mid +1;
			}else{
				// 特殊情况 多个重复数字
				// 此时缩小返回
				right--;
			}
		}
		return numbers[left];
	}
}
