package priv.wzb.leet_code.array.trapping_rain_water_42;

/**
 * @author Satsuki
 * @time 2020/4/4 20:57
 * @description:
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 *
 *
 * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Marcos 贡献此图。
 *
 * 示例:
 *
 * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出: 6
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/trapping-rain-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class Solution {
    public int trap(int[] height) {
        int ans = 0;
        // 第一个与左后一格不会积水
        for (int i = 1; i < height.length - 1; i++) {
            // 初始化左侧右侧的最大值为0
            int maxLeft = 0,maxRight = 0;
            // 从当前向左侧扫描取最大值
            for (int j = i; j >= 0; j--) {
                maxLeft = Math.max(maxLeft,height[j]);
            }
            // 从当前向右侧扫描取最大值
            for (int j = i; j < height.length; j++) {
                maxRight = Math.max(maxRight,height[j]);
            }
            // 不断增加积水
            // 规则就是左右最大高度中较小的一方减去当前高度即为当前一格可积水的高度
            ans+=Math.min(maxLeft,maxRight)-height[i];
        }
        return ans;
    }
}
