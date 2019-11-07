package priv.wzb.datastructure.algorithm.dijkstra;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/6 17:50
 * @description:
 */
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        //邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;// 表示不可以连接
        matrix[0]=new int[]{N,5,7,N,N,N,2};
        matrix[1]=new int[]{5,N,N,9,N,N,3};
        matrix[2]=new int[]{7,N,N,N,8,N,N};
        matrix[3]=new int[]{N,9,N,N,N,4,N};
        // E到C的距离改为1试试
        matrix[4]=new int[]{N,N,8,N,N,5,4};
        // 更改后G到C的最短距离应该为5（成功
//        matrix[4]=new int[]{N,N,1,N,N,5,4};
        matrix[5]=new int[]{N,N,N,4,5,N,6};
        matrix[6]=new int[]{2,3,N,N,4,6,N};

        // 创建图Graph
        Graph graph = new Graph(vertex, matrix);
        graph.showGraph();

        // 测试
        graph.dsj(6);


    }
}

class Graph{
    private char[] vertex; // 顶点
    private int[][] matrix; // 邻接矩阵
    // 这里采用了组合的方式使得类与类之间形成一定的关系
    // 而不是采用继承来使用其他类的属性，在一定程度上解耦了
    // 原来VisitedVertex是可以放到Graph中继承的，但是这样Graph的职责就太重了，进行了分离解耦
    private VisitedVertex vv; //已经访问过的顶点集合

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    // 显示图
    public void showGraph(){
        for(int[] link:matrix){
            System.out.println(Arrays.toString(link));
        }
    }

    // dijkstra算法实现

    /**
     *
     * @param index 出发顶点对应的下标
     */
    public void dsj(int index){
        vv = new VisitedVertex(vertex.length, index);
        // 更新index顶点到周围顶点的距离和前驱顶点
        update(index);

        // 循环n-1次（刚刚开始的第一个顶点初始顶点已经被访问过了。
        // 还剩n-1个顶点需要访问
        for (int j = 1; j < vertex.length; j++) {
            // 选择并返回下一个要访问的顶点
            // 每次都会访问一个节点
            index = vv.updateArr(); // 选择并返回新的访问顶点
            update(index); // 更新index顶点到周围顶点的距离和前驱顶点
        }

        System.out.println(Arrays.toString(vv.dis));
        vv.show();


    }

    /**
     * 每次都是找寻最短的路径，若一开始没被访问过那么路径必定为65535（一个较大值）
     * 随着遍历，其实dijkstra算法会遍历所有的节点的所有路径只不过其实节点不同
     * 每次如果找到更近的距离则会更新从初始节点到该节点的路径长度
     * 例如G到C要取最短路径那么经过GAC即可
     * 在遍历时获取下一个顶点继续遍历时值会取可达顶点
     * 若初始节点的其他可达顶点也可以访问到C比如GEC也可以访问到C
     * 但是从初始节点经过E节点再到C的距离长于GAC那么就不会更新最短距离
     * 其实在访问初始节点的所有可达节点的时候也会更新这些可达节点到可达节点的可达节点的距离
     * 例如要访问C 其实C很后面才被访问 在访问A和E的时候C均为被访问那么C的距离就会被多次判定是否要更新
     * @param index
     */
    // 更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
    private void update(int index){
        int len = 0;
        // 遍历邻接矩阵的matrix[index]行
        for (int j = 0; j < matrix[index].length; j++) {
            // len表示出发顶点到index顶点的距离+index顶点到j顶点的距离的和
            len = vv.getDis(index) + matrix[index][j];
            // 如果j顶点未被访问并且len小于出发顶点到j顶点的距离就需要更新
            // 这里改为或会怎样（把E到C的距离改为1实时是否正确
            // 正确的，就用&&
            // 例如要访问C 其实C很后面才被访问 在访问A和E的时候C均为被访问那么C的距离就会被多次判定是否要更新
            // 所以如果C未被访问那么vv.in()返回的就是false取!就是true,正确无误
            if (!vv.in(j)&& len<vv.getDis(j)){
                // 这里更新的是前驱节点与与距离并没有更新访问过
//            if (!vv.in(j)|| len<vv.getDis(j)){
                // 更新j的前驱顶点
                vv.updatePre(j,index);
                // 更新出发顶点到j顶点的最短距离
                vv.updateDis(j,len);
            }
        }
    }


}

// 已访问顶点集合
class VisitedVertex {
    // 记录各个顶点是否访问过 1表示访问过,0未访问,会动态更新
    public int[] already_arr;
    // 每个下标对应的值为前一个顶点下标, 会动态更新
    public int[] pre_visited;
    // 记录出发顶点到其他所有顶点的距离,比如G为出发顶点，就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放到dis
    public int[] dis;

    public VisitedVertex() {
    }

    /**
     *
     * @param length 顶点个数
     * @param index 出发顶点对应的下标
     */
    public VisitedVertex(int length,int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];

        // 初始化dis数组
        Arrays.fill(dis,65535);
        // 设置出发顶点已经被访问
        this.already_arr[index] = 1;
        // 设置出发顶点到出发顶点的访问距离为0
        this.dis[index] = 0;
    }

    /**
     * 判断index顶点是否被访问过
     * @param index
     * @return 访问过则返回true
     */
    public boolean in(int index){
        return already_arr[index] == 1;
    }

    /**
     * 功能：更新出发顶点到index节点的距离
     * @param index
     * @param len
     */
    public void updateDis(int index,int len){
        dis[index] = len;
    }

    /**
     * 更新顶点pre的前驱为index的节点
     * @param pre 当前顶点下标
     * @param index 当前顶点的前驱顶点下标
     */
    public void updatePre(int pre,int index){
        pre_visited[pre] = index;
    }

    /**
     * 返回出发顶点到Index顶点的距离
     * @param index
     */
    public int getDis(int index){
        return dis[index];
    }

    // 继续选择并返回新的访问顶点，比如这里的G完后就是A作为新的访问顶点
    public int updateArr(){
        // 当前顶点可达的顶点中最短路径
        int min = 65535;
        // 距离当前顶点路径最短的顶点下标
        int index = 0;
        // 这里在进行判定时其实dis中总是保存着正确的被判定过的当前未被访问过的最短可达节点的距离
        for (int i = 0; i < already_arr.length; i++) {
            if (already_arr[i] == 0 && dis[i]<min){
                min = dis[i];
                index = i;
            }
        }

        // 更新index被访问
        already_arr[index] = 1;

        return index;
    }

    // 显示最后结果
    public void show(){
        System.out.println("=====================");
        // 输出already_arr
        for(int i : already_arr){
            System.out.print(i+" ");
        }
        System.out.println();

        // 输出pre_visited
        for(int i : pre_visited){
            System.out.print(i+" ");
        }
        System.out.println();

        // 输出dis
        for(int i : dis){
            System.out.print(i+" ");
        }
        System.out.println();


    }

}
