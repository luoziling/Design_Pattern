package priv.wzb.leet_code.tree_and_graph.as_far_from_land_as_possible_1162;

/**
 * @author Satsuki
 * @time 2020/3/29 20:54
 * @description:
 * 你现在手里有一份大小为 N x N 的『地图』（网格） grid，上面的每个『区域』（单元格）都用 0 和 1 标记好了。其中 0 代表海洋，1 代表陆地，你知道距离陆地区域最远的海洋区域是是哪一个吗？请返回该海洋区域到离它最近的陆地区域的距离。
 *
 * 我们这里说的距离是『曼哈顿距离』（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个区域之间的距离是 |x0 - x1| + |y0 - y1| 。
 *
 * 如果我们的地图上只有陆地或者海洋，请返回 -1。
 *
 *  
 *
 * 示例 1：
 *
 *
 *
 * 输入：[[1,0,1],[0,0,0],[1,0,1]]
 * 输出：2
 * 解释：
 * 海洋区域 (1, 1) 和所有陆地区域之间的距离都达到最大，最大距离为 2。
 * 示例 2：
 *
 *
 *
 * 输入：[[1,0,0],[0,0,0],[0,0,0]]
 * 输出：4
 * 解释：
 * 海洋区域 (2, 2) 和所有陆地区域之间的距离都达到最大，最大距离为 4。
 *  
 *
 * 提示：
 *
 * 1 <= grid.length == grid[0].length <= 100
 * grid[i][j] 不是 0 就是 1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/as-far-from-land-as-possible
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int maxDistance(int[][] grid) {
        // 最远距离
        int maxMD = -1;
        int md;
        // 遍历地图
        // 如果是海洋通过图遍历算法求出最近的陆地距离
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                md=Integer.MAX_VALUE;
                if (grid[i][j]==0){
                    for (int x = 0; x < grid.length; x++) {
                        for (int y = 0; y < grid[i].length; y++) {
                            if(grid[x][y] == 1){
                                // 遍历陆地找出最短距离
                                int n = Math.abs(i-x)+Math.abs(j-y);
                                if (n<md){
                                    md = n;
                                }
                            }
                        }
                    }
                }
                if (md!=Integer.MAX_VALUE&&md>maxMD){
                    maxMD = md;
                }
            }
        }

        return maxMD;

    }
}
