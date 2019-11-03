package priv.wzb.datastructure.sparsearray;

/**
 * @author Satsuki
 * @time 2019/10/28 20:42
 * @description:
 * 稀疏矩阵
 */
public class SparseArray {
    public static void main(String[] args) {
        // 创建一个原始的二维数组 11*11
        // 0：没有棋子
        // 1：表示黑子
        // 2：表示蓝子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        // 输出
        System.out.println("原始的二维数组：");
        printArray(chessArr1);

        // 将二维数组 转 稀疏数组
        // 1. 先遍历二维数组， 得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr1[i][j]!=0){
                    sum++;
                }
            }
        }

        // 2.创建对应的稀疏数组
        // 稀疏数组的行固定是有效数据（非0数据）个数+1，+1是因为要有一条记录来记录整个数组的结构（行列数）
        // 稀疏数组的列固定是3（行，列，具体数据）
        // 但是稀疏数组的第一行存储的是原数组的结构（一共多少行，一共多少列，有效数据个数）
        int sparseArr[][] = new int[sum+1][3];
        // 第一行存储的是原数组的结构（一共多少行，一共多少列，有效数据个数）
        sparseArr[0][0] = 11;  // 一共多少行
        sparseArr[0][1] = 11;  // 一共多少列
        sparseArr[0][2] = 2;   // 有效数据个数

        // 遍历二维数组，将非0值存放到稀疏数组中
        // 一定要进行两次遍历，不然无法创建稀疏数组（sum未知
        // 计数器用来记录是第几个非0数据（有效数据
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                // 注意计数器存放位置
//                count++;
                if (chessArr1[i][j]!=0){
                    // 计数器自增
                    // 自增必须放在前面，因为第一条记录有值用于存放二维数组结构
                    count++;
                    // 稀疏矩阵第一列存放 行
                    sparseArr[count][0] = i;
                    // 稀疏矩阵第一列存放 列
                    sparseArr[count][1] = j;
                    // 稀疏矩阵第一列存放 非0数据（有效数据
                    sparseArr[count][2] = chessArr1[i][j];

//                    // 计数器自增
//                    count++;
                }
            }
        }

        System.out.println("得到的稀疏数组如下所示：");
        printArray(sparseArr);

        // 将稀疏数组转原始的二维数组
        /**
         * 1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的  chessArr2 = int [11][11]
         * 2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
         */

        // 初始化二维数组结构
        int chessArr2[][] = new int[sparseArr[0][0]][sparseArr[0][1]];
        // 恢复数据
        // 2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
        // 从第二行开始
        for (int i = 1; i < sparseArr.length; i++) {
            // 第i行第1列是行号
            // 第i行第2列是列号
            // 第i行第3列是值
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        // 输出恢复后的二维数组
        System.out.println("恢复后的二维数组");
        printArray(chessArr2);
    }

    public static void printArray(int[][] arr){
        // 通过遍历每一行取值打印
        for(int[] row : arr){
            for(int data : row){
                // 格式化打印
                System.out.printf("%d\t",data);
            }
            // 每打印一行打印一个换行符
            System.out.println();
        }
    }
}
