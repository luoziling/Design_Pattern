package priv.wzb.datastructure.algorithm.prim;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/5 20:55
 * @description:
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        // 测试图是否创建成功
        char[] data = {'A','B','C','D','E','F','G'};
        int verx = data.length;
        // 邻接矩阵
        int [][]weight=new int[][]{
                {10000,5,7,10000,10000,10000,2},
                {5,10000,10000,9,10000,10000,3},
                {7,10000,10000,10000,8,10000,10000},
                {10000,9,10000,10000,10000,4,10000},
                {10000,10000,8,10000,10000,5,4},
                {10000,10000,10000,4,5,10000,6},
                {2,3,10000,10000,4,6,10000},};
        // 创建Graph
        MGraph mGraph = new MGraph(verx);
        // 创建一个MinTree对象
        MinTree minTree = new MinTree();
        minTree.createGraph(mGraph,verx,data,weight);
        minTree.showGraph(mGraph);
        System.out.println("测试");
        minTree.prim(mGraph,0);
    }
}

// 创建最小生成树
class MinTree{
    // 创建图的邻接矩阵

    /**
     *
     * @param graph 图对象
     * @param verx 图对应的顶点个数
     * @param data 图各个顶点的值
     * @param weight 图邻接矩阵
     */
    public void createGraph(MGraph graph,int verx,char data[],int[][] weight){
        int i,j;
        for (i = 0; i < verx; i++) {
            // 顶点
            graph.data[i] = data[i];
            for (j = 0;j<verx;j++){
                // 初始化邻接矩阵
                graph.weight[i][j] = weight[i][j];
            }
        }

    }

    // 显示图的邻接矩阵
    public void showGraph(MGraph graph){
        for(int[] link : graph.weight){
            System.out.println(Arrays.toString(link));
        }
    }

    // 编写prim算法，得到最小生成树

    /**
     *
     * @param graph 图
     * @param v 从第几个顶点开始生成
     */
    public void prim(MGraph graph,int v){
        // 标记顶点是否被访问 访问过置一
        int[] visited = new int[graph.verx];

        // 把当前节点标记为已访问
        visited[v] = 1;
        // h1,h1记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000; // 将minWeight初始为较大数
        for (int k = 1; k < graph.verx; k++) {
            //因为有graph.verx个顶点就有graph.verx-1条边来联通所有顶点


            // 每次访问一条边一共访问graph.verx-1条边最后形成一个连通图
            // 下面代码是在被访问过的节点中找寻可以访问未被访问过节点的最短路径
            // 每一次生成的子图和哪个节点的距离最近
            // i节点表示被访问过的节点
            for (int i = 0; i < graph.verx; i++) {
                // j是未被访问过的节点
                for (int j = 0; j < graph.verx; j++) {
                    // 当节点i被访问过且j节点未被访问过且连通ij两个节点的边的权值小于最小权值
                    if (visited[i] == 1 && visited[j] == 0&& graph.weight[i][j]<minWeight){
                        // 更新最小权值以及这条边的两个顶点
                        // 替换minWeight
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            // 每次都找一条最短的边进行记录
            // 找到一条最短边
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值：" + minWeight);
            // 将当前找到的节点标记为已访问过
            visited[h2] = 1;
            // 重置
            minWeight = 10000;
        }

    }
}

class MGraph{
    int verx; //表示图的节点个数
    char[] data;//存放节点数据
    int[][] weight;// 存放边，邻接矩阵

    public MGraph(int verx) {
        this.verx = verx;
        data = new char[verx];
        weight = new int[verx][verx];
    }
}
