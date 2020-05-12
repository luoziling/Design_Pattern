package priv.wzb.leet_code.array.trapping_rain_water_two_407;

import java.util.PriorityQueue;

/**
 * @author Satsuki
 * @time 2019/12/1 23:00
 * @description:
 * 给定一个 m x n 的矩阵，其中的值均为正整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
 *
 *  
 *
 * 说明:
 *
 * m 和 n 都是小于110的整数。每一个单位的高度都大于 0 且小于 20000。
 *
 *  
 *
 * 示例：
 *
 * 给出如下 3x6 的高度图:
 * [
 *   [1,4,3,1,3,2],
 *   [3,2,1,3,2,4],
 *   [2,3,3,2,3,1]
 * ]
 *
 * 返回 4。
 *
 *
 * 如上图所示，这是下雨前的高度图[[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]] 的状态。
 *
 *  
 *
 *
 *
 * 下雨后，雨水将会被存储在这些方块中。总的接雨水量是4。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/trapping-rain-water-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    static final int[] dx = {-1,1,0,0};
    static final int[] dy = {0,0,-1,1};
//    static final int[] dx = new int[]{-1, 0 , 1, 0}, dy = new int[]{0, 1, 0, -1};
    public int trapRainWater(int[][] heightMap) {
        PriorityQueue<Qitem> Q = new PriorityQueue<>();
        if (heightMap.length<3 || heightMap[0].length<3){
            // 行或者列小于3，必然无法积水
            return 0;
        }
        // 初始化行与列
        int row = heightMap.length;
        int column = heightMap[0].length;
        // 定义标记数组
        int[][] mark = new int[row][column];
        // 将四周加入优先级队列（堆）
        for (int i = 0; i < row; i++) {
            // 将第一列都放入堆中
            Q.add(new Qitem(i,0,heightMap[i][0]));
            // 记录第一列都访问过
            mark[i][0] = 1;
            // 将最后一列都放入堆中
            Q.add(new Qitem(i,column-1,heightMap[i][column-1]));
            // 标记最后一列都访问过
            mark[i][column-1] = 1;
        }
        // 访问第一行与最后一行,不访问已经访问过的第一列与最后一列
        for (int i = 1; i < column - 1; i++) {
            Q.add(new Qitem(0,i,heightMap[0][i]));
            mark[0][i] = 1;
            Q.add(new Qitem(row-1,i,heightMap[row-1][i]));
            mark[row-1][i] = 1;
        }

        // 最终积水量
        int result = 0;
        // 想象成从外向内积水
        while (!Q.isEmpty()){
            Qitem head = Q.poll();
            int x = head.getX();
            int y = head.getY();
            int h = head.getH();

            // 扩展四个方向进行搜索
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                // 如果越界或者已经访问过
                if (newX<0|| newX>=row|| newY<0||newY>=column||(mark[newX][newY]==1)){
                    // 跳过该点继续尝试其他扩展
                    continue;
                }
                if (h>heightMap[newX][newY]){
                    // 如果该点的高度大于扩展的新点的高度
                    // 说明可以进行积水，想象成水流漫过较高的点向内流
                    result += h-heightMap[newX][newY];
                    // 更新图，扩展点的高度经过积水会变得和原先的点的高度一样
                    heightMap[newX][newY] = h;
                }
                // 可以搜索的扩展点入堆，便于以后从扩展的点开始继续搜索
                Q.offer(new Qitem(newX,newY,heightMap[newX][newY]));
                // 标记访问过
                mark[newX][newY] = 1;

//                if (newX>=0&& newX<row&& newY>=0&&newY<column&&(mark[newX][newY]==0)){
//                    mark[newX][newY] = 1;
//                    int tempHeight = heightMap[newX][newY];
//                    if (h>tempHeight){
//                        result+=(h-tempHeight);
//
//                    }
//                    Q.offer(new Qitem(newX,newY,Math.max(h,tempHeight)));
//                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] a = new int[][]{{9,9,9,9,9,9,8,9,9,9,9},{9,0,0,0,0,0,1,0,0,0,9},{9,0,0,0,0,0,0,0,0,0,9},{9,0,0,0,0,0,0,0,0,0,9},{9,9,9,9,9,9,9,9,9,9,9}};
        System.out.println(new Solution().trapRainWater(a));

    }
}

/**
 * 模仿三维，记录某一点的长宽高
 */
class Qitem implements Comparable<Qitem>{
    private int x;
    private int y;
    private int h;

    // 根据高度排序，方便后续构建堆的时候根据高度构建为小顶堆，总是从较低处开始搜索寻找可以流入水的缺口
    @Override
    public int compareTo(Qitem o) {
        return this.h-o.h;
    }

    public Qitem(int x, int y, int h) {
        this.x = x;
        this.y = y;
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "Qitem{" +
                "x=" + x +
                ", y=" + y +
                ", h=" + h +
                '}';
    }
}
