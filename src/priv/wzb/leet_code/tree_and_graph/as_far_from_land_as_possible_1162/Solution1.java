package priv.wzb.leet_code.tree_and_graph.as_far_from_land_as_possible_1162;


import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Satsuki
 * @time 2020/3/29 20:54
 * @description: 你现在手里有一份大小为 N x N 的『地图』（网格） grid，上面的每个『区域』（单元格）都用 0 和 1 标记好了。其中 0 代表海洋，1 代表陆地，你知道距离陆地区域最远的海洋区域是是哪一个吗？请返回该海洋区域到离它最近的陆地区域的距离。
 * <p>
 * 我们这里说的距离是『曼哈顿距离』（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个区域之间的距离是 |x0 - x1| + |y0 - y1| 。
 * <p>
 * 如果我们的地图上只有陆地或者海洋，请返回 -1。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：[[1,0,1],[0,0,0],[1,0,1]]
 * 输出：2
 * 解释：
 * 海洋区域 (1, 1) 和所有陆地区域之间的距离都达到最大，最大距离为 2。
 * 示例 2：
 * <p>
 * <p>
 * <p>
 * 输入：[[1,0,0],[0,0,0],[0,0,0]]
 * 输出：4
 * 解释：
 * 海洋区域 (2, 2) 和所有陆地区域之间的距离都达到最大，最大距离为 4。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= grid.length == grid[0].length <= 100
 * grid[i][j] 不是 0 就是 1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/as-far-from-land-as-possible
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution1 {
    public int maxDistance(int[][] grid) {
        // 移动数组
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        // 队列用于存储陆地
        Queue<int[]> queue = new ArrayDeque<>();

        int m = grid.length, n = grid[0].length;
        // 所有陆地入队
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        // 从各个陆地开始遍历海洋，找到的海洋就是离陆地最远的海洋
        boolean hasOcean = false;
        int[] point = null;
        while (!queue.isEmpty()) {
            // 取出并去除队列头节点
            point = queue.poll();
            int x = point[0], y = point[1];
            // 去除队列元素，将其四周海洋入队
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                if (newX<0||newX>=m||newY<0||newY>=n || grid[newX][newY]!=0){
                    continue;
                }
                // 将原本是海洋的代指（0）变成距离陆地的距离
                grid[newX][newY] = grid[x][y] +1;
                hasOcean = true;
                queue.offer(new int[]{newX,newY});
            }
        }

        // 没有陆地或海洋返回-1
        if (point == null || !hasOcean) {
            return -1;
        }
        // 返回最后依次遍历
        return grid[point[0]][point[1]]-1;

    }
}
