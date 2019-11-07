package priv.wzb.datastructure.algorithm.horse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Satsuki
 * @time 2019/11/6 21:53
 * @description:
 */
public class HorseChessboard {
    private static int X; // 棋盘的列
    private static int Y; //棋盘的行
    // 创建一个数组，标记棋盘的各个位置是否被访问过
    private static boolean visited[];
    // 使用一个属性标记是否棋盘所有位置都被访问过了
    private static boolean finished; // true表示成功

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
    /**
     * 完成骑士周游问题的算法
     * @param chessboard 棋盘
     * @param row 马儿当前的位置的行 从0开始
     * @param column 马儿当前的位置的列  从0开始
     * @param step 是第几步 ,初始位置就是第1步
     */
    public static void traversalChessboard1(int[][] chessboard, int row, int column, int step) {
        chessboard[row][column] = step;
        //row = 4 X = 8 column = 4 = 4 * 8 + 4 = 36
        visited[row * X + column] = true; //标记该位置已经访问
        //获取当前位置可以走的下一个位置的集合
        ArrayList<Point> ps = next1(new Point(row, column));
        //对ps进行排序,排序的规则就是对ps的所有的Point对象的下一步的位置的数目，进行非递减排序
        sort(ps);
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
     * 完成骑士周游问题
     * @param chessboard 棋盘
     * @param row 🐎的行
     * @param column 🐎的列
     * @param step 是第几步
     */
    public static void traversalChessboard(int[][] chessboard,int row,int column,int step){
        chessboard[row][column] = step;
        visited[row * X + column] = true; //标记该位置已访问
        // 获取当前位置可以走的位置
        ArrayList<Point> ps = next(new Point(column, row));
        while (!ps.isEmpty()){
            // 取出下一个可以走的位置
            Point p = ps.remove(0);
            // 判断是否访问过
            if (!visited[p.y * X+ p.x]){
                traversalChessboard(chessboard,p.y,p.x,step+1);
            }
        }
        // 判断🐎是否走完
        if (step<X*Y && !finished){
            chessboard[row][column] = 0;
            visited[row * X+column] = false;
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

    /**
     * 根据当前位置，计算🐎能走哪些位置
     * @param curPoint
     * @return
     */
    public static ArrayList<Point> next(Point curPoint){
        // 创建一个ArrayList
        ArrayList<Point> ps = new ArrayList<>();
        // 创建一个Point
        Point p1 = new Point();
        // 判断🐎能不能继续往下走
        //0代表边缘
        // 先走x轴
        if ((p1.x = curPoint.x - 2)>=0&&(p1.y = curPoint.y-1)>=0){
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x - 2)>=0&&(p1.y = curPoint.y+1)<Y){
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 2)<X&&(p1.y = curPoint.y-1)>=0){
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 2)<X&&(p1.y = curPoint.y+1)<Y){
            ps.add(new Point(p1));
        }

        // 先走y轴
        if ((p1.x = curPoint.x - 1)>=0&&(p1.y = curPoint.y-2)>=0){
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 1)<X&&(p1.y = curPoint.y-2)>=0){
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x - 1)>=0&&(p1.y = curPoint.y+2)<Y){
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 1)<X&&(p1.y = curPoint.y+2)<Y){
            ps.add(new Point(p1));
        }

        return ps;
    }
    // 根据当前这一步的所有的下一步的选择位置，进行非递减排序
    public static void sort(ArrayList<Point> ps){
        ps.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                // 获取到O1的下一步的所有位置个数
                ArrayList<Point> next = next(o1);
                ArrayList<Point> next1 = next(o2);
                if (next.size()<next1.size()){
                    return -1;
                }else if (next.size() == next.size()){
                    return 0;
                }else {
                    return 1;
                }
            }
        });
    }
}
