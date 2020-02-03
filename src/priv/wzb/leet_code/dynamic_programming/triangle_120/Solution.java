package priv.wzb.leet_code.dynamic_programming.triangle_120;

import java.util.ArrayList;
import java.util.Arrays;
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
public class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        // 检测数据是否合格
        if (triangle.size() == 0){
            return 0;
        }
        // dp数组保存每个点的最优解
        List<List<Integer>> dp = new ArrayList<>();
        // 初始化dp
        for (int i = 0; i < triangle.size(); i++) {
            List<Integer> sub = new ArrayList<>();
            for (int j = 0; j < triangle.get(i).size(); j++) {
                sub.add(0);
            }
            dp.add(sub);
        }
//        System.out.println(dp.toString());
        
        // 初始化dp的最后一行
        for (int i = 0; i < dp.get(dp.size()-1).size(); i++) {
            // 脑子抽了使用get获取了数据，然后对数据进行了赋值肯定出错
//            dp.get(dp.size()-1).get(i) = triangle.get(triangle.size()-1).get(i);
            // 直接使用set
            dp.get(dp.size()-1).set(i,triangle.get(triangle.size()-1).get(i));
        }

        // dp 利用动态规划函数进行求解，从下向上
        for (int i = dp.size()-2; i >=0 ; i--) {
            for (int j = 0; j < dp.get(i).size(); j++) {
//                dp[i][j] = dp[i][j] + Math.min(dp[i+1][j],dp[i+1][j+1])
                dp.get(i).set(j,triangle.get(i).get(j) + Math.min(dp.get(i+1).get(j),dp.get(i+1).get(j+1)));
            }
        }
        return dp.get(0).get(0);
    }

    public static void main(String[] args) {
        List<List<Integer>> dp = new ArrayList<>();
        // 初始化dp
//        for (int i = 0; i < 4; i++) {
//            List<Integer> sub = new ArrayList<>();
//            for (int j = 0; j < i+1; j++) {
//                sub.add(1);
//            }
//            dp.add(sub);
//        }

        List<Integer> sub = new ArrayList<>();
        sub.add(2);
        dp.add(sub);

        List<Integer> sub1 = new ArrayList<>();
        sub1.add(3);
        sub1.add(4);
        dp.add(sub1);

        List<Integer> sub2 = new ArrayList<>();
        sub2.add(6);
        sub2.add(5);
        sub2.add(7);
        dp.add(sub2);

        List<Integer> sub3 = new ArrayList<>();
        sub3.add(4);
        sub3.add(1);
        sub3.add(8);
        sub3.add(3);
        dp.add(sub3);

        System.out.println(new Solution().minimumTotal(dp));


//        int[][] a = new int[][]{
//                {2},
//                {3,4},
//                {6,5,7},
//                {4,1,8,3}
//        };
//        List<List<Integer>> triangle = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            List<int[]> ints = Arrays.asList(a[i]);
//            triangle.add();
//        }

//        System.out.println(dp.toString());
//        Integer a = dp.get(0).get(0);
//        Integer integer = dp.get(0).get(0);
//        List<Integer> list = dp.get(0);
//        list.
//        a =
//        System.out.println(dp);
    }
}
