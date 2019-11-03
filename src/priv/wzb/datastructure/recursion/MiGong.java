package priv.wzb.datastructure.recursion;

/**
 * @author Satsuki
 * @time 2019/10/30 15:21
 * @description:
 */
public class MiGong {
    public static void main(String[] args) {
        // 创建一个二维数组，模拟迷宫
        // 地图
        int[][] map = new int[8][7];
        // 使用1表示墙
        // 上下全部置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        // 左右全部置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        System.out.println("地图的情况");
        printMap(map);
        // 设置挡板，1表示
        map[3][1] = 1;
        map[3][2] = 1;
        // 堵死
//        map[1][2] = 1;
//        map[2][2] = 1;
        System.out.println("地图的情况");
        printMap(map);

        // 使用递归回溯给小球找路
//        setWay(map,1,1);
        setWay1(map,1,1);
        // 输出新的地图，小球走过并标识过的地图
        System.out.println("地图的情况（小球走过）");
        printMap(map);



    }

    // 使用递归回溯给小球找路

    /**
     *
     * @param map 表示地图
     * @param i 从哪个位置开始找
     * @param j
     * @return 如果找到通路就返回true，否则返回false
     * 如果小球能到map[6][5]表示找到通路结束
     * 约定；当map[i][j] = 0表示未走过，当map[i][j] = 1表示墙不走，当map[i][j] = 2表示通路可走
     * 当map[i][j] = 3表示该位置已经走过但是走不通
     * 在走迷宫需要确定一个策略（方法）down->right>up->left,如果该点走不通就回溯
     */
    public static boolean setWay(int[][] map,int i,int j){
        if (map[6][5] == 2){
            //通路OK
            return true;
        }else {
            if (map[i][j] == 0){
                //当前这个点未走过
                // 按照策略走down->right>up->left
                map[i][j] = 2; // 假定该点可以走通
                if (setWay(map,i+1,j)){
                    // 向下走
                    return true;
                }else if (setWay(map,i,j+1)){
                    // 向右走
                    return true;
                }else if (setWay(map,i-1,j)){
                    // 向上走
                    return true;
                }else if (setWay(map,i,j-1)){
                    // 向左走
                    return true;
                }else {
                    // 说明该点走不通，是死路
                    map[i][j] = 3;
                    return false;
                }
            }else {
                // 如果map[i][j] != 0,可能是1，2，3
                return false;
            }
        }
    }

    // 修改找路策略  改为上右下左
    public static boolean setWay1(int[][] map,int i,int j){
        if (map[6][5] == 2){
            //通路OK
            return true;
        }else {
            if (map[i][j] == 0){
                //当前这个点未走过
                // 按照策略走
                map[i][j] = 2; // 假定该点可以走通
                if (setWay1(map,i-1,j)){
                    // 向上走
                    return true;
                }else if (setWay1(map,i,j+1)){
                    // 向右走
                    return true;
                }else if (setWay1(map,i+1,j)){
                    // 向下走
                    return true;
                }else if (setWay1(map,i,j-1)){
                    // 向左走
                    return true;
                }else {
                    // 说明该点走不通，是死路
                    map[i][j] = 3;
                    return false;
                }
            }else {
                // 如果map[i][j] != 0,可能是1，2，3
                return false;
            }
        }
    }

    public static void printMap(int[][] map){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
