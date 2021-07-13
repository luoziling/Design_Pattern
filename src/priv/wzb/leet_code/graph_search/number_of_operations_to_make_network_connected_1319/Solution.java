package priv.wzb.leet_code.graph_search.number_of_operations_to_make_network_connected_1319;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2021-01-23 17:07
 * @description:
 * 1319. 连通网络的操作次数
 **/

public class Solution {
	List<Integer>[] edges;
	boolean[] used;
	public int makeConnected(int n, int[][] connectins) {
		// 每次在图中找出一条多余的边，将其断开，并连接图中的两个连通分量。将这个过程重复 k-1k−1 次，最终就可以使得整个图连通。
		if (connectins.length < n-1){
			// 如果N个点但边小于N-1则永远无法全联通
			return -1;
		}
		// 初始化N条边
		edges = new List[n];
		for (int i = 0; i < n; i++) {
			edges[i] = new ArrayList<>();
		}
		for (int[] connectin : connectins) {
			// 无向图 初始化互联
			edges[connectin[0]].add(connectin[1]);
			edges[connectin[1]].add(connectin[0]);
		}
		used = new boolean[n];
		int ans = 0;
		for (int i = 0; i < n; i++) {
			// 从某个节点开始，找到所有相连的节点
			// 如果找到到达不了的节点就需要增加移动次数
			// 由于证明过连通分量会多一条边，所以就是这条边的移动来联通无法到达的节点
			if (!used[i]){
				dfs(i);
				++ans;
			}
		}
		// -1是因为第一次多余的搜索
		return ans -1;
	}
	public void dfs(int u){
		used[u] = true;
		for (int v : edges[u]) {
			if (!used[v]){
				dfs(v);
			}
		}
	}
}
