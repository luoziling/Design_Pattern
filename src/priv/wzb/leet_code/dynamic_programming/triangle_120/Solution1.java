package priv.wzb.leet_code.dynamic_programming.triangle_120;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/12/3 20:14
 * @description:
 * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
 *
 * 例如，给定三角形：
 *
 *
 *      [2],
 *     [3,4],
 *    [6,5,7],
 *   [4,1,8,3]
 * ]
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 *
 * 说明：
 *
 * 如果你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题，那么你的算法会很加分。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/triangle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 利用动态规划求解
 * 从下向上推导
 * 设置一个dp二维数组dp[i][j]就等于原三角形的[i][j]位置的最优解
 * 可以发现最后一行就是边界从下向上推的时候最后一行的数字就代表了该点的最小数字
 * 然后向上进行寻找一直寻找到顶部
 * 原始问题：求三角形从顶向下的最优解-》拆分成子问题：从三角形从底至上的每一个点的最优解
 * 转换公式：dp[i][j] = triangle[i][j] + Math.min(dp[i+1][j],dp[i+1][j+1])
 * 公式描述：当前点的最优解就等于当前点的值加上它的下一层可达点中选取一个最小值(其实是下一层的最优解的最小值)进行相加那么就得到了当前点的最小值
 */
public class Solution1 {
    public int minimumTotal(List<List<Integer>> triangle) {
        int row = triangle.size();
        // dp数组
        int[] minlen = new int[row+1];
        for (int level = row-1; level >=0 ; level--) {
            // 双重循环，搜索到了二维数组中的每个数
            // 没有借用其他的二维数组，把二维数组进行了降维，转换为了一维数组进行记录和计算
            for (int i = 0; i <= level; i++) {
                minlen[i] = triangle.get(level).get(i) + Math.min(minlen[i],minlen[i+1]);
            }
        }
        return minlen[0];
    }


}
