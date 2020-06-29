package priv.wzb.datastructure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/11/4 17:58
 * @description:
 * 深度=》中序遍历
 * 广度=》层次遍历
 */
public class Graph {
    private ArrayList<String> vertexList; // 存储顶点集合
    private int[][] edges; // 存储图对应的邻接矩阵
    private int numOfEdges; // 边的数目

    // 定义一个数组记录某个顶点是否被访问
    private boolean[] isVisited;

    public static void main(String[] args) {
        // 节点个数
        int n = 5;
        String vertexValue[] = {"a","b","c","d","e"};
        // 创建图
        Graph graph = new Graph(n);
        // 循环添加顶点
        for(String value : vertexValue){
            graph.insertVertex(value);
        }
        // 添加边
        // AB AC BC BD BE
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);

        graph.showGraph();

        //测试dfs
        System.out.println("depth first Search");
//        graph.dfs();

        //广度优先
        System.out.println("广度优先");
        graph.bfs();
    }

    // 构造器
    public Graph(int n){
        // 初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0;
        isVisited = new boolean[n];
    }

    // 得到第一个邻接节点的下标w

    /**
     * 寻找第一个有值的邻接节点
     * @param index
     * @return 存在返回下标不存在返回-1
     */
    public int getFirstNeighbor(int index){
        // 通过邻接矩阵来判断节点间是否可达
        // index代表第几行 j用于遍历这一行（也就是说遍历该节点所有可能可达的节点找寻第一个可达节点
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[index][j]>0){
                // 找到下一个存在的邻接节点
                return j;
            }
        }
        return -1;
    }

    // 根据前一个邻接节点的下标来获取下一个邻接节点
    // 回溯用

    /**
     *
     * @param v1 在哪一行（以什么为基准
     * @param v2 在哪一列（以传过来的列为基准向下寻找直到找到下一个有值的
     * @return
     */
    public int getNextNeighor(int v1,int v2){
        for (int j = v2+1; j < vertexList.size(); j++) {
            if (edges[v1][j]>0){
                return j;
            }
        }
        return -1;
    }

    /**
     * 广度优先遍历于深度优先遍历类似更类似于层次遍历
     * 对要遍历的数组进行循环遍历，每次遍历一个元素时根节点永远都是这个元素
     * 将这个元素的全部节点遍历完之后在继续遍历下一个节点（从遍历过的节点队列中取
     * @param isVisited
     * @param i
     */
    // 广度优先遍历
    private void bfs(boolean[] isVisited,int i){
        int u ; // 表示队列头节点对应的下标
        int w; //邻接节点w
        // 队列，记录节点访问顺序
        LinkedList queue = new LinkedList();
        // 访问节点，输出节点信息
        System.out.print(getValueByIndex(i)+"=>");
        //标记已访问
        isVisited[i] = true;
        // 将节点加入队列
        queue.addLast(i);
        while (!queue.isEmpty()){
            // 去除队列头节点下标
            u = (Integer)queue.removeFirst();
            w = getFirstNeighbor(u);
            while (w != -1){
                if (!isVisited[w]){
                    System.out.print(getValueByIndex(w)+"=>");
                    // 标记已访问
                    isVisited[w] = true;
                    // 入队
                    queue.addLast(w);
                }
                // 以u为前驱找w后面的下一个邻接点
                w = getNextNeighor(u,w); // 体现出广度优先
            }
        }
    }

    // 遍历所有的节点都进行广度优先搜索
    public void bfs(){
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]){
                bfs(isVisited,i);
            }
        }
    }

    /**
     * 深度优先遍历类似于中序遍历
     * 维持一个循环对第所有元素进行深度优先遍历
     * 将遍历过的元素输出并且标记为遍历过下次就不再遍历
     * 遍历第一个节点的下一个节点，如果找到则输出该节点并标识为访问过
     * 然后以下一个节点为根节点找寻下一个节点可达的下一个节点不停重复循环
     * @param isVisited
     * @param i
     */
    // 深度优先遍历
    public void dfs(boolean[] isVisited,int i){
        // 首先访问该节点，输出
        System.out.print(getValueByIndex(i) + "->");
        // 将该节点设置为已访问
        isVisited[i] = true;
        int w = getFirstNeighbor(i);
        // 存在
        while (w != -1){
            // 说明有邻接节点
            if (!isVisited[w]){
                dfs(isVisited,w);
            }
            // 如果w已经被访问
            // 查找邻接节点的下一个节点
            w = getNextNeighor(i,w);
        }
    }

    // 对dfs进行重载，遍历所有节点并进行dfs
    public void dfs(){
        // 遍历所有节点进行dfs[回溯]
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]){
                // 未被访问
                dfs(isVisited,i);
            }
        }
    }
    
    // 常用方法
    // 返回节点个数
    public int getNumOfVertex(){
        return vertexList.size();
    }
    // 得到边的数目
    public int getNumOfEdges(){
        return numOfEdges;
    }

    // 返回节点i（下标)对应的数据
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    //返回v1 v2的权值
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }
    // 显示图对应的矩阵
    public void showGraph(){
        for(int[] link:edges){
            System.out.println(Arrays.toString(link));
        }
    }

    // 插入节点
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    // 添加边

    /**
     *
     * @param v1 顶点1
     * @param v2 顶点2
     * @param weight 边权值
     *               使用v1和v2代表连起来的一条边
     */
    public void insertEdge(int v1,int v2,int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }
}
