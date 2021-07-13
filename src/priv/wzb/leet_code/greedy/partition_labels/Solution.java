package priv.wzb.leet_code.greedy.partition_labels;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-10-22 19:52
 * @description:
 **/

public class Solution {
	public List<Integer> partitionLabels(String S) {
		// 容器，记录各字符最后一次出现的位置
		int[] last = new int[26];
		int length = S.length();
		for (int i = 0; i < length; i++) {
			// 每次遍历某个字符都重置一次最后出现的位置
			last[S.charAt(i) - 'a'] = i;
		}
		List<Integer> partition = new ArrayList<>();
		int start = 0, end = 0;
		for (int i = 0; i < length; i++) {
			// 不断刷新分片
			// end在遍历过程中不断变为某个字符出现的最后位置，遍历到最接近的就切片
			// 尽可能多的分片
			end = Math.max(end,last[S.charAt(i)-'a']);
			// 当找到某个字符的最后出现位置
			if (i == end){
				// 分片
				partition.add(end-start+1);
				// 开启下一个分片
				start = end+1;
			}
		}
		return partition;
	}
}
