package priv.wzb.leet_code.array.offer_04;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-08 19:21
 * @description:
 **/

public class Solution {
	public boolean findNumberIn2DArray(int[][] matrix, int target) {
		for (int[] subMatrix : matrix) {
			boolean res = find(subMatrix, target);
			if (res){
				return true;
			}
		}
		return false;
	}

	public boolean find(int[] nums,int target){
		int left = 0,right = nums.length-1;
		while (left <= right){
			int mid = (left+right)/2;
			if (nums[mid] < target){
				left = mid+1;
			}else if (nums[mid]> target){
				right = mid-1;
			}else{
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		int[][] matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
		System.out.println("new Solution().findNumberIn2DArray(matrix,20) = " + new Solution().findNumberIn2DArray(matrix, 20));
	}
}
