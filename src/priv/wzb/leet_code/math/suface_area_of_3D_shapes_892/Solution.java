package priv.wzb.leet_code.math.suface_area_of_3D_shapes_892;

/**
 * @author Satsuki
 * @time 2020/3/25 13:06
 * @description:
 * 在 N * N 的网格上，我们放置一些 1 * 1 * 1  的立方体。
 *
 * 每个值 v = grid[i][j] 表示 v 个正方体叠放在对应单元格 (i, j) 上。
 *
 * 请你返回最终形体的表面积。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：[[2]]
 * 输出：10
 * 示例 2：
 *
 * 输入：[[1,2],[3,4]]
 * 输出：34
 * 示例 3：
 *
 * 输入：[[1,0],[0,2]]
 * 输出：16
 * 示例 4：
 *
 * 输入：[[1,1,1],[1,0,1],[1,1,1]]
 * 输出：32
 * 示例 5：
 *
 * 输入：[[2,2,2],[2,1,2],[2,2,2]]
 * 输出：46
 *  
 *
 * 提示：
 *
 * 1 <= N <= 50
 * 0 <= grid[i][j] <= 50
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/surface-area-of-3d-shapes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int surfaceArea(int[][] grid) {
        // 用于移动的辅助数列
        // 用于xy轴坐标系的上，右，下，左移动
        int[] dr = new int[]{0,1,0,-1};
        int[] dc = new int[]{1,0,-1,0};
        int N = grid.length;
        int ans = 0;

        // ij用于遍历棋盘上的每个格子
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 当某个格子有方块（>0）
                if (grid[i][j]>0){
                    // 计算上下的单位面积
                    ans+=2;
                    // 计算四周的单位面积
                    for (int k = 0; k < 4; k++) {
                        // 向四周扩散，找到四周的高度
                        int ni = i+dr[k];
                        int nj = j+dc[k];
                        // 初始化， nv代表某一边的高度
                        // 某个方向没东西就高度为0
                        int nv = 0;
                        if (0<=ni&& ni<N && 0<=nj && nj<N){
                            nv = grid[ni][nj];
                        }

                        // 累加答案
                        ans+=Math.max(grid[i][j] - nv,0);
                    }
                }
            }
        }

        return ans;


    }
}
