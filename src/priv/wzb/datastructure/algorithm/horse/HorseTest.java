package priv.wzb.datastructure.algorithm.horse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Satsuki
 * @time 2019/11/7 22:09
 * @description:
 */
public class HorseTest {
    private static int X;// 棋盘的行
    private static int Y;// 棋盘的列
    private static boolean[] visited;// 是否被访问过
    // 是否全都访问过
    private static boolean finished;
    public static void main(String[] args) {
        System.out.println("骑士周游算法，开始运行~~");
        //测试骑士周游算法是否正确
        X = 8;
        Y = 8;
        int row = 1; //马儿初始位置的行，从1开始编号
        int column = 1; //马儿初始位置的列，从1开始编号
//        X = 6;
//        Y = 6;
//        int row = 4; //马儿初始位置的行，从1开始编号
//        int column = 3; //马儿初始位置的列，从1开始编号
        //创建棋盘
        int[][] chessboard = new int[X][Y];
        visited = new boolean[X * Y];//初始值都是false
        //测试一下耗时
        long start = System.currentTimeMillis();
        traversalChessboard1(chessboard, row - 1, column - 1, 1);
        long end = System.currentTimeMillis();
        System.out.println("共耗时: " + (end - start) + " 毫秒");

        //输出棋盘的最后情况
        for(int[] rows : chessboard) {
            for(int step: rows) {
                System.out.print(step + "\t");
            }
            System.out.println();
        }
    }

    public static void traversalChessboard1(int[][] chessboard, int row, int column, int step) {
        chessboard[row][column] = step;
        //row = 4 X = 8 column = 4 = 4 * 8 + 4 = 36
        visited[row * X + column] = true; //标记该位置已经访问
        //获取当前位置可以走的下一个位置的集合
        ArrayList<Point> ps = next1(new Point(row,column));
        //对ps进行排序,排序的规则就是对ps的所有的Point对象的下一步的位置的数目，进行非递减排序
        sort1(ps);
        //遍历 ps
        while(!ps.isEmpty()) {
            Point p = ps.remove(0);//取出下一个可以走的位置
            //判断该点是否已经访问过
            if(!visited[p.x * X + p.y]) {//说明还没有访问过
                traversalChessboard1(chessboard, p.x, p.y, step + 1);
            }
        }
        //判断马儿是否完成了任务，使用   step 和应该走的步数比较 ，
        //如果没有达到数量，则表示没有完成任务，将整个棋盘置0
        //说明: step < X * Y  成立的情况有两种
        //1. 棋盘到目前位置,仍然没有走完
        //2. 棋盘处于一个回溯过程
        if(step < X * Y && !finished ) {
            chessboard[row][column] = 0;
            visited[row * X + column] = false;
        } else {
            finished = true;
        }

    }

    /**
     *
     * @param chessboard 棋盘
     * @param row 🐎从第几行开始
     * @param col 🐎从第几列开始
     * @param step 是第几步
     */
    public static void traversalChessboard(int[][] chessboard,int row,int col,int step){
        // 设置访问步数
        chessboard[row][col] = step;
        // 设置已访问
        visited[row*X+ col] = true;

        // 存放🐎接下去可以走的路径
        ArrayList<Point> ps = next(row,col);

        // 优化
        sort(ps);
//        System.out.println(ps.toString());

        for(Point point : ps){
            // 未被访问过
            if (!visited[point.x*X+point.y]){
                traversalChessboard(chessboard,point.x,point.y,step+1);
            }

        }

        if (step < X*Y && finished == false){
            chessboard[row][col] = 0;
            visited[row * X + col] = false;
        }else {
            finished = true;
        }


    }

    /**
     * 功能： 根据当前位置(Point对象)，计算马儿还能走哪些位置(Point)，并放入到一个集合中(ArrayList), 最多有8个位置
     * @param curPoint
     * @return
     */
    public static ArrayList<Point> next1(Point curPoint) {
        //创建一个ArrayList
        ArrayList<Point> ps = new ArrayList<Point>();
        //创建一个Point
        Point p1 = new Point();
        //表示马儿可以走5这个位置
        if((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y -1) >= 0) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走6这个位置
        if((p1.x = curPoint.x - 1) >=0 && (p1.y=curPoint.y-2)>=0) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走7这个位置
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走0这个位置
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走1这个位置
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走2这个位置
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走3这个位置
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        //判断马儿可以走4这个位置
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        return ps;
    }

    public static ArrayList<Point> next(int row,int col){
        ArrayList<Point> ps = new ArrayList<>();
        int r = row;
        int c = col;
        Point p = new Point();
        // 走一步仍然在棋盘上未跨界则把这一步加入ps
        if ((row = row-2)>=0&& (col = col-1)>=0){
            ps.add(new Point(row,col));
        }
        row = r;
        col = c;
        if ((row = row-2)>=0&& (col = col+1)<Y){
            ps.add(new Point(row,col));
        }
        row = r;
        col = c;
        if ((row = row+2)<X&& (col = col-1)>=0){
            ps.add(new Point(row,col));
        }
        row = r;
        col = c;
        if ((row = row+2)<X&& (col = col+1)<Y){
            ps.add(new Point(row,col));
        }
        row = r;
        col = c;
        if ((row = row-1)>=0&& (col = col+2)<Y){
            ps.add(new Point(row,col));
        }
        row = r;
        col = c;
        if ((row = row+1)<X&& (col = col+2)<Y){
            ps.add(new Point(row,col));
        }
        row = r;
        col = c;
        if ((row = row-1)>=0&& (col = col-2)>=0){
            ps.add(new Point(row,col));
        }
        row = r;
        col = c;
        if ((row = row+1)<X&& (col = col-2)>=0){
            ps.add(new Point(row,col));
        }
        row = r;
        col = c;

        return ps;
    }

    // 排序优化（贪心算法）每次都挑选可以走位置的下一步可以走位置最小的 根据下一步可走位置多少进行排序
    public static void sort(ArrayList<Point> ps){
        Collections.sort(ps, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                ArrayList<Point> next = next(o1.x, o1.y);
                ArrayList<Point> next1 = next(o2.x, o2.y);
                if (next.size()<next1.size()){
                    return -1;
                }else if (next.size() == next1.size()){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
    }
    public static void sort1(ArrayList<Point> ps){
        Collections.sort(ps, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                ArrayList<Point> next = next1(o1);
                ArrayList<Point> next1 = next1(o2);
                if (next.size()<next1.size()){
                    return -1;
                }else if (next.size() == next1.size()){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
    }
}
//class Point{
//    int x;
//    int y;
//
//    public Point() {
//    }
//
//    public Point(Point p) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public Point(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
//
//    @Override
//    public String toString() {
//        return "Point{" +
//                "x=" + x +
//                ", y=" + y +
//                '}';
//    }
//}
