package priv.wzb.leet_code.math.permutation_sequence_60;

import java.util.Arrays;

/**
 * @program: Design_Pattern
 * @description:
 * 给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
 *
 * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
 *
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * 给定 n 和 k，返回第 k 个排列。
 *
 * 说明：
 *
 * 给定 n 的范围是 [1, 9]。
 * 给定 k 的范围是[1,  n!]。
 * 示例 1:
 *
 * 输入: n = 3, k = 3
 * 输出: "213"
 * 示例 2:
 *
 * 输入: n = 4, k = 9
 * 输出: "2314"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutation-sequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangzibai
 * @create: 2020-09-05 15:01
 *
 *
 * 全排列+剪枝
 *
 * 全排列发现所有真正的排列结果都在最底层
 *
 * 计算每次选择某位数字后产生的可能（n-i的阶乘种可能）
 * 与当前的k做比较，进行剪枝，并逐渐减少k
 *
 * 不断缩减找到k所代表的数列在哪一种子树下
 *
 * https://leetcode-cn.com/problems/permutation-sequence/solution/hui-su-jian-zhi-python-dai-ma-java-dai-ma-by-liwei/
 **/

public class Solution {

    /**
     * 记录数字是否使用过
     */
    private boolean[] used;

    /**
     * 阶乘数组
     */
    private int[] factorial;

    private int n;
    private int k;

    public String getPermutation(int n, int k) {
        // 暴力思路，从小到大放入队列取第N个
        // 不现实，换一种思路
        // 想不出
        // 0
        this.n = n;
        this.k = k;
        // 计算一共有多少种可能
        // 以及每个子数列有多少种可能
        // 根据定理集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
        // 选定元素导致元素不断减少的情况下也可以了解下次的剪枝可以省略查找多少种数列组合
        calculateFactorial(n);

        // 查找全排列需要的布尔数组
        // 数组表示数字的使用情况[1,2,3,…,n]每个数字只能使用一次
        used = new boolean[n+1];
        Arrays.fill(used,false);
        StringBuilder path = new StringBuilder();
        // 深度优先遍历
        dfs(0,path);
        return path.toString();
    }

    /**
     * 深度遍历+剪枝查找要找的排列情况
     * @param index 在这一步之前已经选择了几个数组，其值恰好等于这一步 需要确定的下标位置
     * @param path 遍历的路径也就是最后产生的结果
     */
    private void dfs(int index,StringBuilder path){
        // 设定返回点，如果遍历到给定的第n个数组就返回
        if (index==n){
            return;
        }
        // 计算还未确定的数组的全排列个数，第1此进入是n-1
        // 计算选择当前路径下可产生的排列数量，以便剪枝
        int cnt = factorial[n-1-index];
        // 尝试用[1..n]每个数字作为当前数列位置的内容
        for (int i = 1; i <= n; i++) {
            // 数字被用过就跳过
            // 1-n的排列每个数字只出现一次
            if (used[i]){
                continue;
            }
            // 剪枝后还需要排列几位
            // 根据选择当前数字会产生的全排序数列数量剪枝
            // 通过选择当前数字产生的全排序数列数量与要的第k个数列做比较
            // 如果k>cnt就说明需要剪枝在这条分支下找不到要的第k个数列
            if (cnt<k){
                // 剪枝后k的查找也需要减少因为已经查过了前面的数列都小于第k个
                k-=cnt;
                // 剪枝后直接返回优化性能，避免浪费，剪枝后就不用去查找已经剪过的可能数列了
                continue;
            }
            // 选择当前数字（选择当前路径确定第k个数列就在当前分支下
            path.append(i);
            // 设定数字已被访问
            used[i] = true;
            // 深度遍历迭代，向下一层进发（不断向后确认要选的数字
            dfs(index+1,path);

            // 没有回溯的必要
            // 已经找到了当前位置的数字后面的没必要再去尝试
            return;

        }
    }

    /**
     * 计算阶乘数组
     * @param n
     */
    private void calculateFactorial(int n){
        factorial = new int[n+1];
        factorial[0]=1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i-1]*i;
        }
    }
}
