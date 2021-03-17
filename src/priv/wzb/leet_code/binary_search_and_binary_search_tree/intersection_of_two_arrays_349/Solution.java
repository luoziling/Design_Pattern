package priv.wzb.leet_code.binary_search_and_binary_search_tree.intersection_of_two_arrays_349;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-11-02 20:48
 * @description:
 **/

public class Solution {
	public int[] intersection(int[] nums1, int[] nums2) {
		Set<Integer> res = new HashSet<>();
		Set<Integer> set = new HashSet<>();
		for (int j = 0; j < nums1.length; j++) {
			set.add(nums1[j]);
		}
		for (int k = 0; k < nums2.length; k++) {
			if (set.contains(nums2[k])){
				res.add(nums2[k]);
			}
		}
		int[] res1 = new int[res.size()];
		int i = 0;
		for (Integer integer : res) {
			res1[i++] = integer;
		}
		return res1;
	}
}
