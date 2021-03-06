package priv.wzb.datastructure.algorithm.kruskal;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Satsuki
 * @time 2019/11/5 21:36
 * @description:
 * 重点
 * 一般来说生成最小生成树的图是没有方向的也就是无向图
 * 但是在kruskal算法中是有将边加入的方向的此时的路径是有向的所以才能找寻终点
 */
public class KruskalCase {
    private int edgeNum; // 记录边个数
    private char[] vertexs; // 顶点数组
    private int[][] matrix; // 邻接矩阵
    // INF表示两个顶点不能联通
    private static final int INF = Integer.MAX_VALUE;
    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //克鲁斯卡尔算法的邻接矩阵
        int matrix[][] = {
                        /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {   0,  12, INF, INF, INF,  16,  14},
                /*B*/ {  12,   0,  10, INF, INF,   7, INF},
                /*C*/ { INF,  10,   0,   3,   5,   6, INF},
                /*D*/ { INF, INF,   3,   0,   4, INF, INF},
                /*E*/ { INF, INF,   5,   4,   0,   2,   8},
                /*F*/ {  16,   7,   6, INF,   2,   0,   9},
                /*G*/ {  14, INF, INF, INF,   8,   9,   0}};
        //创建KruskalCase 对象实例
        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        //输出构建的
        kruskalCase.print();
        EData[] edges = kruskalCase.getEdges();
        System.out.println("排序前");
        System.out.println(Arrays.toString(edges));
        System.out.println("排序后");
        kruskalCase.sortEdges(edges);
        System.out.println(Arrays.toString(edges));
        // 生成最小生成树
        kruskalCase.kruskal();


    }


    public KruskalCase(char[] vertexs, int[][] matrix) {
        int vlen = vertexs.length;
        this.vertexs = vertexs;
        this.matrix = matrix;
        // 统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i+1; j < vlen; j++) {
                if (this.matrix[i][j] != INF){
                    // 联通
                    edgeNum ++;
                }
            }
        }
    }

    public void kruskal(){
        int index = 0; // 表示最后结果的索引
        // 用于保存终点，这里十分巧妙的进行终点保存，通过不停的遍历数组中不仅保存着的可能是终点下标或终点的前一个点的下标
        // 因为终点是与要加入树的点距离最大的点，那么显然
        // 如果在初次加入时候一定有个点在数组中必定会保持0这个点就是最初加入的边的终点
        // 这个终点肯定是距离后来加入点距离最大的点或者最大点的前一个顶点
        // 终点就是在最小生成树树中距离某一点最远的点
        int[] ends = new int[edgeNum]; // 用于保存在最小生成树中保存的每个顶点在最小生成树中的终点的集合
        // 创建结果数组，保存最后的最小生成树
        EData[] rets = new EData[edgeNum];
        // 获取途中所有边的集合
        EData[] edges = getEdges();
        System.out.println("图的边的集合=" + Arrays.toString(edges) + " 共" + edges.length+ "条边");
        // 对边根据权值排序
        sortEdges(edges);
        // 遍历edges数组，将边添加到最小生成树时判断准备加入的边是否生成回路
        // 其实就是一个很简单的过程
        // 先获取要加入边的头节点和尾节点
        // 然后寻找这两个节点在树中的end节点（终点）
        // 如果初次加入那么终点就是自己（在数组中存放的是0）
        // 否则就跳着遍历数组寻找该顶点的终点即可
        for (int i = 0; i < edgeNum; i++) {
            // 获取到第i条边的起点
            // getPosition用于返回
            int p1 = getPosition(edges[i].start);
            // 获取终点
            int p2 = getPosition(edges[i].end);

            // 获取p1这个顶点在已有的最小生成树中的终点
            int m = getEnd(ends, p1); // 若第一次加入那么终点也就是自己
            // 获取p2这个顶点的终点
            int n = getEnd(ends,p2);// 判断要加入的下一个点的终点
            // 判断是否构成回路
            if (m== n){
                //构成回路
                continue;
            }else {
                // 因为在生成树之前的边的数组其实已经从小到达排序过
                // 所以每次取的边肯定是最小的边，如果不构成回路即可直接使用
                // 每次都挑选一条要加入的边，并且将其开始顶点的终点设置为它的终点
                // 这个终点可能是其他顶点的起点，所以在getEnd中是循环的遍历ends数组的这样就可以找到距离最远的点也就是终点
                // 一开始的末尾节点就是头节点的末尾节点
                // 在ends数组中始终存在着一个距离其他顶点最远的顶点
                // 这个顶点是动态的是会被改变的
                ends[m] = n; // 设置m在已有最小生成树中的终点
//                ends[n] = n; // 没有必要写，因为在找寻最终节点的时候其实是while循环，每次顺序加入之后经过循环就可以找到终点
                //可以加入
                rets[index++] = edges[i];
            }
        }

        // 统计并打印最小生成树
        System.out.println("最小生成树");
        for (int i = 0; i < index; i++) {
            System.out.println(rets[i]);
        }
//        System.out.println("最小生成树:" + Arrays.toString(rets));
    }

    public void print(){
        System.out.println("邻接矩阵：");
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                System.out.printf("%15d\t",matrix[i][j]);
            }
            System.out.println();
        }
    }

    // 对边进行排序处理

    /**
     *
     * @param edges 边的集合
     */
    private void sortEdges(EData[] edges){
        // 冒泡排序
//        EData temp;
//        for (int i = 0; i < edges.length-1; i++) {
//            for (int j = 0; j < edges.length - i - 1; j++) {
//                if (edges[j].weight>edges[j+1].weight){
//                    temp = edges[j];
//                    edges[j] = edges[j+1];
//                    edges[j+1] = temp;
//                }
//            }
//        }
        //Java排序
        // 利用Java自带的Collections工具类排序所有边的长度
        Collections.sort(Arrays.asList(edges));
    }

    /**
     *
     * @param ch 顶点的值
     * @return ch顶点对应的下标，找不到返回-1
     */
    private int getPosition(char ch){
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch){
                // 找到
                return i;
            }
        }
        //找不到
        return -1;
    }

    /**
     * 获取图中边放到数组中
     * 其实在这个方法中就表明了代表边的开始顶点和末尾顶点其实是可以调换的只要可以表示这条边即可
     * 这条边是无向边
     * @return
     */
    private EData[] getEdges(){
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            // 遍历上三角
            // 只要遍历代表距离的矩阵的上三角即可找出所有的边（因为顶点A到顶点B与顶点B到顶点A的边是同一条这是无向边）
            for (int j = i+1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF){
                    edges[index++]  = new EData(vertexs[i],vertexs[j],matrix[i][j]);
                }
            }
        }

        return edges;
    }

    /**
     * 图构成最小生成树的算法kruskal算法的核心之一
     * 这里的终点是对于加入的边来说的例如加入边<A,B>那么B就是终点
     * 获取下标为i的顶点的终点(动态加入），用于后面判断两个顶点的重点是否相同（构建最小生成树的时候）
     * @param ends 在遍历过程中逐步形成的记录各个顶点对应的终点
     * @param i 顶点对应的下标
     * @return 下标为i的顶点的终点的下标
     */
    private int getEnd(int[] ends,int i){
        // 获取一个点的终点
        // 在形成终点的过程中其实会遍历ends这个数组
        while (ends[i] != 0){
            i = ends[i];
        }
        // 如果没有被加入过，第一次被加入，那么终点就是自己
        return i;
    }
}

// 创建一个类EData,它的对象实例表示一条边
class EData implements Comparable<EData>{
    char start; //边的起点
    char end; // 边的终点
    int weight; //边的权值

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public int compareTo(EData o) {
        return this.weight-o.weight;
    }

    @Override
    public String toString() {
        return "EData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }
}
