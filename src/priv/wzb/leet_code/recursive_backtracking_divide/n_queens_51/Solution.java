package priv.wzb.leet_code.recursive_backtracking_divide.n_queens_51;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/20 16:53
 * @description:
 * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 *
 *
 *
 * 上图为 8 皇后问题的一种解法。
 *
 * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
 *
 * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 *
 * 示例:
 *
 * 输入: 4
 * 输出: [
 *  [".Q..",  // 解法 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // 解法 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 * 解释: 4 皇后问题存在两个不同的解法。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/n-queens
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 切记！！：在初始化或者复制赋值过程中不要让句柄指向了同一个对象，记得每次都要新建对象
 * 例如在这段代码中我在初始化location时，一开始并没有使用新的char数组对象，使用了同一个char数组对象构成了list
 * 那么我在对这个char数组对象修改的时候每次修改都会影响到List中的所有char数组
 */
public class Solution {
    // 这两个代表了棋盘八个方向的移动x与y要走的路
    // 方向数组
    static final int dx[] = {-1,1,0,0,-1,-1,1,1};
    static final int dy[] = {0,0,-1,1,-1,1,-1,1};
    int length;

    List<List<String>> result = new LinkedList<>();
    public List<List<String>> solveNQueens(int n) {
        length = n;
        int[][] mark = new int[n][n]; // 标记棋盘是否可以放置皇后的二维数组
//        List<String> location = new LinkedList<>(); // 存储某个摆放位置
        List<char[]> location = new LinkedList<>(); // 存储某个摆放位置
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < n; i++) {
//            sb.append('.');
//        }
        // wrong!!
//        char[] c = new char[n];
//        for (int i = 0; i < n; i++) {
//            c[i] = '.';
//        }
        // 初始化
        for (int i = 0; i < n; i++) {
            char[] c = new char[n];
            for (int j = 0; j < n; j++) {
                mark[i][j] = 0;
//                location.get(i)[j]= '.';
                c[j] = '.';
            }
            location.add(c);
        }
        generate(0,n,location,mark);

        return result;
    }

    /**
     *
     * @param k 当前 在摆放第几个皇后
     * @param n 一共要摆放多少个皇后，也代表了棋盘是n*n的
     * @param location 记录某次可行的中间结果
     * @param mark 棋盘
     */
    void generate(int k,int n,List<char[]> location,int[][] mark){
        // 放满了皇后，保存当前结果并返回
        if(k == n){
            List<String> temp = new LinkedList<>();
            // 将char数组转为String
            StringBuilder sb;
            for (int i = 0; i < n; i++) {
                sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(location.get(i)[j]);
                }
                temp.add(sb.toString());
            }
            result.add(new LinkedList<>(temp));
            return;
        }
        // 每次一共要放n个皇后
        // i代表棋盘的列
        for (int i = 0; i < n; i++) {
            // 若当前位置可以放置皇后
            if (mark[k][i] == 0){
                // 记录回溯前mark的镜像
//                int[][] markMirror = new int[n][n];
                int[][] markMirror;
                markMirror = copy(mark);
//                for (int j = 0; j < n; j++) {
//                    for (int l = 0; l < n; l++) {
//                        markMirror[j][l] = mark[j][l];
//                    }
//                }
                // 记录当前皇后位置,第k个皇后也代表了放在第几行
                location.get(k)[i] = 'Q';
                // 放置皇后
                putDownTheQueen(k,i,mark);
                // 递归下一行皇后
                generate(k+1,n,location,mark);
                // 使mark变为回溯前的状态
                mark = markMirror;
                // 重置尝试放置皇后的位置为.
                location.get(k)[i] = '.';
            }
        }

    }

    public int[][] copy(int[][] mark){
        int[][] markMirror = new int[length][length];
        for (int j = 0; j < length; j++) {
            for (int l = 0; l < length; l++) {
                markMirror[j][l] = mark[j][l];
            }
        }
        return markMirror;
    }


    /**
     * 放置皇后与更新皇后可攻击的位置（也就是不能再放皇后的位置
     * @param x
     * @param y
     * @param mark
     */
    void putDownTheQueen(int x,int y,int[][] mark){
        mark[x][y] = 1;
        int newX;
        int newY;
        // 这边是不固定的随着棋盘大小进行改变
        for (int i = 1; i < mark.length; i++) {
            // 八个方向，每个方向都延申1至N-1
            for (int j = 0; j < 8; j++) {
                // 新的方向向八个方向延申，每个方向最多延申N-1
                newX = x+i*dx[j];
                newY = y+i*dy[j];
                // 判断该坐标（延申后的坐标）是否在棋盘内
                if (newX>=0 && newX<mark.length&&newY>=0&&newY<mark.length){
                    // 在棋盘内,更新棋盘
                    mark[newX][newY] = 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().solveNQueens(4));
    }

}
