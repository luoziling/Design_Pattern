package priv.wzb.leet_code.math.the_last_number_left_in_the_circle_62;

import java.util.ArrayList;

/**
 * @author Satsuki
 * @time 2020/3/30 18:24
 * @description:
 * 0, 1, , n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。求出这个圆圈里剩下的最后一个数字。
 *
 * 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。
 *
 *  
 *
 * 示例 1：
 *
 * 输入: n = 5, m = 3
 * 输出: 3
 * 示例 2：
 *
 * 输入: n = 10, m = 17
 * 输出: 2
 *  
 *
 * 限制：
 *
 * 1 <= n <= 10^5
 * 1 <= m <= 10^6
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution1 {
    public int lastRemaining(int n, int m) {
        // 数学解法
        // 约瑟夫问题留到最后肯定只剩1个数字
        // 那么从1个数字可以逆推到只剩两个数字时候这个数字的位置
        // 再向上逆推即可得到这个数字位于原来数列中的位置
        // 逆推公式：(ans+m)%i
        // ans代表答案最后留下来的是第几个 i代表当前一共有多少数字留下
        int ans = 0;
        for (int i = 2; i <= n; i++) {
            ans = (ans+m)%i;
        }

        return ans;

    }
}
