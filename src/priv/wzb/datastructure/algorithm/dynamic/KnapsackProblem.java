package priv.wzb.datastructure.algorithm.dynamic;

/**
 * @author Satsuki
 * @time 2019/11/4 21:28
 * @description:
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        int[] w= {1,4,3}; // 物品的重量
        int [] val = {1500,3000,2000}; // 物品的价值
        int m = 4; // 背包的容量
        int n = val.length; // 物品的个数

        // 为了记录放入商品的情况，我们放入一个二维数组
        int[][] path = new int[n+1][m+1];

        // 创建二维数组
        // 有0行0列所以+1
        // v[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] v = new int[n+1][m+1];

        // 初始化第一行第一列，在本程序中可以不去处理，默认为0
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0; // 将第一列设置为0
        }
        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0; // 将第一行设置为0
        }

        System.out.println("处理前");
        // 输出以下v看看目前的情况
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }

        // 根据前面的公式来动态规划处理
        // i代表第i+1个物品
        for (int i = 1; i < v.length; i++) {
            // 不处理第0行
            // j代表背包容量
            for (int j = 1; j < v[0].length; j++) {
                // 不处理第0列
                if (w[i-1]>j){// 因为我们程序i是从1开始的，因此原来公式中的w[i]改为w[i-1]
                    v[i][j] = v[i-1][j];
                }else {
//                    v[i][j] = Math.max(v[i-1][j],val[i-1]+v[i-1][j-w[i-1]]);
                    // 为了记录商品存放到背包的情况，不能直接的使用公式
                    if (v[i-1][j]<val[i-1]+v[i-1][j-w[i-1]]){
                        v[i][j] = val[i-1]+v[i-1][j-w[i-1]];
                        // 记录到path
                        path[i][j] = 1;
                    }else {
                        v[i][j] = v[i-1][j];
                    }
                }
            }
        }

        System.out.println("处理后");
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("输出放入的商品");
        // 输出放入的是哪些商品
        int i = path.length-1; // 行的最大小标
        int j = path[0].length -1; // 列的最大小标
        while (i>0&& j >0){
            // 从path的最后开始找
            if (path[i][j] == 1){
                System.out.printf("第%d个商品放入到背包\n",i);
                j-= w[i-1];
            }
            i--;
        }
    }
}
