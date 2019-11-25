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
 * 这里的解法是宽搜bfs
 * https://leetcode-cn.com/problems/course-schedule/solution/course-schedule-tuo-bu-pai-xu-bfsdfsliang-chong-fa/
 *
 */

public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 入度表，用于统计课程安排图中每个节点的入度
        int[] indegrees = new int[numCourses];
        // 根据给出的课程关系图的邻接矩阵求出每门课程的入度（在学这门课程之前需要学哪些课程
        // cp[0] 就代表了学该门课程，如果找到一个配对就说明该门课程有一门前置课程
        for(int[] cp : prerequisites) indegrees[cp[0]]++;
        LinkedList<Integer> queue = new LinkedList<>();
        // 将最优入度为0的节点入队
        for (int i = 0; i < numCourses; i++) {
            if (indegrees[i] == 0)
                queue.addLast(i);
        }
        while (!queue.isEmpty()){
            Integer pre = queue.removeFirst();
            // 入度为0说明这门课程没有前置课程可以学习
            // 学完后将总课程自减1
            numCourses--;
            for(int[] req : prerequisites){
                // 找出以pre为前置课程的所有课程
                if (req[1]!=pre) continue;
                // 在学习课程之前进行一个判定
                // 因为假设已经学过了pre这个前置课程的前提下
                // 此时待学课程的入度即可-1
                // 如果在-1之后发现该课程入度为0说明可以学习该课程，加入队列
                if (--indegrees[req[0]] == 0)queue.addLast(req[0]);
            }

        }
        // 看看是否学完所有课程

        return numCourses == 0;
    }
}
