package priv.wzb.leet_code.math.the_last_number_left_in_the_circle_62;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
public class Solution {
    public int lastRemaining(int n, int m) {
        // 存储数组
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        // 用来指代要删除的数字的下标
        int idx = 0;
        while (n>1){
            // 这个算式的由来:
            // 首先由于是有规律的所以不需要遍历直接往后数m个数字就可以直到下一个要删除的数字位置
            // 由于把当前位置删除了所以下一次要删除时要-1
            // 其实我的想法是由于数列是0-n的所以删除时需要-1
            // 由于是循环删除所以%n。超过个数上限后重新映射回数组内
            idx = (idx+m-1)%n;
            list.remove(idx);
            n--;
        }

        return list.get(0);

    }
}
