package priv.wzb.leet_code.tree_and_graph.course_schedule_207;

import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/11/24 15:34
 * @description:
 * 现在你总共有 n 门课需要选，记为 0 到 n-1。
 *
 * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]
 *
 * 给定课程总量以及它们的先决条件，判断是否可能完成所有课程的学习？
 *
 * 示例 1:
 *
 * 输入: 2, [[1,0]]
 * 输出: true
 * 解释: 总共有 2 门课程。学习课程 1 之前，你需要完成课程 0。所以这是可能的。
 * 示例 2:
 *
 * 输入: 2, [[1,0],[0,1]]
 * 输出: false
 * 解释: 总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0；并且学习课程 0 之前，你还应先完成课程 1。这是不可能的。
 * 说明:
 *
 * 输入的先决条件是由边缘列表表示的图形，而不是邻接矩阵。详情请参见图的表示法。
 * 你可以假定输入的先决条件中没有重复的边。
 * 提示:
 *
 * 这个问题相当于查找一个循环是否存在于有向图中。如果存在循环，则不存在拓扑排序，因此不可能选取所有课程进行学习。
 * 通过 DFS 进行拓扑排序 - 一个关于Coursera的精彩视频教程（21分钟），介绍拓扑排序的基本概念。
 * 拓扑排序也可以通过 BFS 完成。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/course-schedule
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 这道题目转换成了判断该图是否是有向无环图
 * prerequisites是图的邻接矩阵
 * 这里的解法是深搜bfs
 * https://leetcode-cn.com/problems/course-schedule/solution/course-schedule-tuo-bu-pai-xu-bfsdfsliang-chong-fa/
 *
 */

public class Solution1 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 调整，记录源课程邻接矩阵的当前课程和前置课程反序的过程
        int[][] adjacency = new int[numCourses][numCourses];
        int[] flags = new int[numCourses];
        for(int[] cp : prerequisites){
            adjacency[cp[1]][cp[0]] = 1;
        }
        for (int i = 0; i < numCourses; i++) {
            // 深搜每个节点，如果发现有环
            if (!dfs(adjacency,flags,i))
                return false;
        }
        return true;
    }

    /**
     *
     * @param adjacency 调整后的矩阵，第一个代表前置课程，第二个代表当前课程
     * @param flags 判定当前节点的访问状态 -1代表被其他节点访问过，0代表未被访问，1代表被当前节点访问
     * @param i
     * @return
     */
    private boolean dfs(int[][] adjacency,int[] flags,int i){
        // 在本轮DFS搜索中节点i被第二次访问，即课程图有向有环直接返回false
        if (flags[i] == 1) return false;

        // 当前访问节点已被其他节点启动的DFS访问，无需再重复搜索，直接返回true
        if (flags[i] == -1) return true;
        // 当前正在访问，将这个节点置为1表示本次DFS搜索中i节点被访问过
        flags[i] = 1;
        for (int j = 0; j < adjacency.length; j++) {
            // 节点之间存在连线，并且发现环直接返回false
            if (adjacency[i][j] == 1 && !dfs(adjacency,flags,j))
                return false;
        }
        // 当前节点所有邻接节点已被访问，并没发现环
        // 则将flag置为-1代表访问过并且返回true
        flags[i] = -1;
        return true;
    }
}
