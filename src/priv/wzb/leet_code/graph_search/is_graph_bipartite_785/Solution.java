package priv.wzb.leet_code.graph_search.is_graph_bipartite_785;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2020/7/16 10:01
 * @description:
 * 给定一个无向图graph，当这个图为二分图时返回true。
 *
 * 如果我们能将一个图的节点集合分割成两个独立的子集A和B，并使图中的每一条边的两个节点一个来自A集合，一个来自B集合，我们就将这个图称为二分图。
 *
 * graph将会以邻接表方式给出，graph[i]表示图中与节点i相连的所有节点。每个节点都是一个在0到graph.length-1之间的整数。这图中没有自环和平行边： graph[i] 中不存在i，并且graph[i]中没有重复的值。
 *
 *
 * 示例 1:
 * 输入: [[1,3], [0,2], [1,3], [0,2]]
 * 输出: true
 * 解释:
 * 无向图如下:
 * 0----1
 * |    |
 * |    |
 * 3----2
 * 我们可以将节点分成两组: {0, 2} 和 {1, 3}。
 *
 * 示例 2:
 * 输入: [[1,2,3], [0,2], [0,1,3], [0,2]]
 * 输出: false
 * 解释:
 * 无向图如下:
 * 0----1
 * | \  |
 * |  \ |
 * 3----2
 * 我们不能将节点分割成两个独立的子集。
 * 注意:
 *
 * graph 的长度范围为 [1, 100]。
 * graph[i] 中的元素的范围为 [0, graph.length - 1]。
 * graph[i] 不会包含 i 或者有重复的值。
 * 图是无向的: 如果j 在 graph[i]里边, 那么 i 也会在 graph[j]里边。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/is-graph-bipartite
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 思路：遍历图，某个节点的相邻节点置为与原节点不同的颜色，
 * 如果遍历过程中没遇到已染色的图说明符合要求
 */
public class Solution {
    private static final int UNCOLORED = 0;
    private static final int RED=1;
    private static final int GREEN=2;
    // 存储各节点颜色
    private int[] color;
    // 是否符合二分图要求
    private boolean valid;
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        valid = true;
        color = new int[n];
        // 初始化填充为未涂色状态
        Arrays.fill(color,UNCOLORED);
        for (int i = 0; i < n && valid; i++) {
            if (color[i] == UNCOLORED){
                dfs(i,RED,graph);
            }
        }
        return valid;
    }

    /**
     *
     * @param node 节点
     * @param c 待填充的颜色
     * @param graph 图
     */
    private void dfs(int node, int c, int[][] graph) {
        // 填充颜色
        color[node] = c;
        // 相邻点应填充的颜色
        int cNei = c==RED?GREEN:RED;
        // 深度遍历，遍历完一个节点的所有节点再去遍历其他节点
        for (int neighbor:graph[node]) {
            if (color[neighbor] == UNCOLORED){
                dfs(neighbor,cNei,graph);
                // 如果无法构成二分图直接返回
                if (!valid){
                    return;
                }
            }else if (color[neighbor] != cNei){
                // 如果相邻节点的颜色与预期的另一种颜色不符则说明无法构成二分图
                // valid置为false并返回
                valid = false;
                return;
            }
        }
    }


    public static void main(String[] args) {

        int a = 1,b=1;
        // &&的短路机制
        if (a++==2&&b++==2){
            System.out.println(a + ":" + b);
        }

        System.out.println(a + ":" + b);
    }
}
