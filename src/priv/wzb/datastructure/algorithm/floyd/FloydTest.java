package priv.wzb.datastructure.algorithm.floyd;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/7 21:58
 * @description:
 */
public class FloydTest {
    public static void main(String[] args) {
        // 测试看看图是否创建成功
        char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        //创建邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;
        matrix[0] = new int[] { 0, 5, 7, N, N, N, 2 };
        matrix[1] = new int[] { 5, 0, N, 9, N, N, 3 };
        matrix[2] = new int[] { 7, N, 0, N, 8, N, N };
        matrix[3] = new int[] { N, 9, N, 0, N, 4, N };
        matrix[4] = new int[] { N, N, 8, N, 0, 5, 4 };
        matrix[5] = new int[] { N, N, N, 4, 5, 0, 6 };
        matrix[6] = new int[] { 2, 3, N, N, 4, 6, 0 };

        //创建 Graph 对象
        FGraph graph = new FGraph(vertex.length, vertex,matrix);
        //调用弗洛伊德算法
        graph.floyd();
        graph.show();
    }
}

class FGraph{
    // 顶点
    char[] vertex;
    // 前驱节点
    int[][] pre;
    // 节点间的距离
    int[][] dis;

    public FGraph(int length,char[] vertex, int[][] dis) {
        this.pre = new int[length][length];
        this.vertex = vertex;
        this.dis = dis;
        for (int i = 0; i < length; i++) {
            Arrays.fill(pre[i],i);
        }
    }

    public void floyd(){

        // 中间节点k
        for (int k = 0; k < vertex.length; k++) {
            // 初始节点i
            for (int i = 0; i < vertex.length; i++) {
                // 到达节点j
                for (int j = 0; j < vertex.length; j++) {
                    int len = dis[i][k]+ dis[k][j];
                    if (len<dis[i][j]){
                        dis[i][j] = len;
                        // 更新前驱表
                        pre[i][j] = k;
                    }
                }
            }
        }
    }

    // 显示pre数组和dis数组
    public void show() {

        //为了显示便于阅读，我们优化一下输出
        char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        for (int k = 0; k < dis.length; k++) {
            // 先将pre数组输出的一行
            for (int i = 0; i < dis.length; i++) {
                System.out.print(vertex[pre[k][i]] + " ");
            }
            System.out.println();
            // 输出dis数组的一行数据
            for (int i = 0; i < dis.length; i++) {
                System.out.print("("+vertex[k]+"到"+vertex[i]+"的最短路径是" + dis[k][i] + ") ");
            }
            System.out.println();
            System.out.println();

        }

    }
}
