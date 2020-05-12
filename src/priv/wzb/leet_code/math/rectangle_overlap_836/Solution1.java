package priv.wzb.leet_code.math.rectangle_overlap_836;

/**
 * @author Satsuki
 * @time 2020/3/18 15:40
 * @description:
 * 矩形以列表 [x1, y1, x2, y2] 的形式表示，其中 (x1, y1) 为左下角的坐标，(x2, y2) 是右上角的坐标。
 *
 * 如果相交的面积为正，则称两矩形重叠。需要明确的是，只在角或边接触的两个矩形不构成重叠。
 *
 * 给出两个矩形，判断它们是否重叠并返回结果。
 *
 *
 *
 * 示例 1：
 *
 * 输入：rec1 = [0,0,2,2], rec2 = [1,1,3,3]
 * 输出：true
 * 示例 2：
 *
 * 输入：rec1 = [0,0,1,1], rec2 = [1,0,2,1]
 * 输出：false
 *
 *
 * 提示：
 *
 * 两个矩形 rec1 和 rec2 都以含有四个整数的列表的形式给出。
 * 矩形中的所有坐标都处于 -10^9 和 10^9 之间。
 * x 轴默认指向右，y 轴默认指向上。
 * 你可以仅考虑矩形是正放的情况。
 *
 */
public class Solution1 {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        // 矩阵相交，相交的图案也是矩形，判断两个矩阵投影到x轴和y轴的线段是否有交集即可
        // 当x轴有两条线段A,B，当其中较为左边线段的右侧大于较为右边的线段的左侧就相交

        return (Math.min(rec1[2],rec2[2])>Math.max(rec1[0],rec2[0])&&
                Math.min(rec1[3],rec2[3])>Math.max(rec1[1],rec2[1])
                );
    }
}
