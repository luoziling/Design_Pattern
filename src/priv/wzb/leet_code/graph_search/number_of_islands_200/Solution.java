package priv.wzb.leet_code.graph_search.number_of_islands_200;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/30 21:42
 * @description:
 * 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
 *
 * 示例 1:
 *
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 *
 * 输出: 1
 * 示例 2:
 *
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 *
 * 输出: 3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    // 用于标记搜索过的岛屿
    int mark[][];
    //方向数组
    static final int[] dx = {-1,1,0,0};
    static final int[] dy = {0,0,-1,1};
    // 用于辅助宽搜的队列
    LinkedList<MyPoint> queue;

    // DFS从某个点开始判定可以扩展的岛屿

    /**
     *
     * @param grid 地图
     * @param x x坐标
     * @param y y坐标
     */
    void DFS(char[][] grid,int x,int y){
        // 当前点搜索过了
        mark[x][y] = 1;
        // 从点（x,y）开始向四个方向扩展进行搜索
        for (int i = 0; i < 4; i++) {
            // 进行四个方向移动后的点
            int newX = x+dx[i];
            int newY = y+dy[i];

            // 移动后的x或y坐标越界
            if (newX<0||newX>=mark.length||newY<0||newY>=mark[newX].length){
                // 不进行深搜遍历下一个方向
                continue;
            }
            // 当移动后的点未遍历过且在地图上该点是陆地
            if (mark[newX][newY] == 0&&grid[newX][newY]=='1'){
                // 继续深搜
                DFS(grid,newX,newY);
            }

        }
    }


    /**
     * 宽度优先搜索
     * @param grid 地图
     * @param x 横坐标x
     * @param y 纵坐标y
     */
    void BFS(char[][] grid,int x,int y){
        // 入队
        queue.addLast(new MyPoint(x,y));
        // 标记访问过
        mark[x][y] = 1;
        // 队列不为空继续宽搜
        while (!queue.isEmpty()){
            // 取出队头
            MyPoint point = queue.pop();
            x = point.getX();
            y = point.getY();
            for (int i = 0; i < 4; i++) {
                // 拓展四个方向
                int newX = x+dx[i];
                int newY = y+dy[i];
                // 越界继续下一个方向
                if (newX<0||newY<0||newX>=mark.length||newY>=mark[newX].length){
                    continue;
                }
                // 为访问过且该点是陆地
                if (mark[newX][newY] == 0&&grid[newX][newY]=='1'){
                    // 入队
                    queue.addLast(new MyPoint(newX,newY));
                    // 标记访问过
                    mark[newX][newY] = 1;
                }
            }
        }
    }


    public int numIslands(char[][] grid) {
        // 岛屿数量
        int islandNum = 0;
        // 初始化
        queue = new LinkedList<>();
        mark = new int[grid.length][];
        for (int i = 0; i < mark.length; i++) {
            int[] a = new int[grid[i].length];
            // 初始化为0
            for (int j = 0; j < a.length; j++) {
                a[j] = 0;
            }
            // 放入二维数组
            mark[i] = a;
        }
        // 遍历地图从每一个点触发搜索岛屿
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (mark[i][j] == 0&&grid[i][j] == '1'){
                    // 深搜或广搜
                    DFS(grid,i,j);
//                    BFS(grid,i,j);
                    islandNum++;
                }
            }
        }
        return islandNum;
    }

    public static void main(String[] args) {
        char[][] grid = {{'1','1','1','1','0'},{'1','1','0','1','0'},{'1','1','0','0','0'},{'0','0','0','0','0'}};

        System.out.println(new Solution().numIslands(grid));
    }
}

// 用于记录x与y左边代表的一个点
class MyPoint{
    private int x;
    private int y;

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
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

    @Override
    public String toString() {
        return "MyPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
