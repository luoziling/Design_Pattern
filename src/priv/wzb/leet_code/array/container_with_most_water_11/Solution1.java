package priv.wzb.leet_code.array.container_with_most_water_11;

/**
 * @author Satsuki
 * @time 2020/2/15 21:13
 * @description:
 * # 盛最多水的容器
 *
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 *
 * ![](https://aliyun-lc-upload.oss-cn-hangzhou.aliyuncs.com/aliyun-lc-upload/uploads/2018/07/25/question_11.jpg)
 *
 * 图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 *
 *
 *
 * 示例:
 *
 * 输入: [1,8,6,2,5,4,8,3,7]
 * 输出: 49
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/container-with-most-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution1 {
    public int maxArea(int[] height) {
        // 双指针，筛选出可能面积较大的一些点然后进行计算比较
        // l代表左侧指针最开始指向最左侧的点，r代表最右侧指针，指向左右侧的点
        int maxArea = 0, l = 0, r = height.length-1;
        while (l<r){
            // 计算面积每次都取最大值
            // 计算由原来ij替换为用lr表示某个点
            maxArea = Math.max(maxArea,Math.min(height[l],height[r]) * (r-l));
            // 移动指针
            if (height[l]<height[r]){
                // 当左侧指针小于右侧指针的（高度）时候，向右移动l指针
                l++;
            }else {
                r--;
            }
        }

        return maxArea;
    }
}
