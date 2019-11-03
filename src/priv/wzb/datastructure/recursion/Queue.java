package priv.wzb.datastructure.recursion;

/**
 * @author Satsuki
 * @time 2019/10/30 16:12
 * @description: 八皇后问题
 * 理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，
 * 用一个一维数组即可解决问题. arr[8] = {0 , 4, 7, 5, 2, 6, 1, 3}
 * //对应arr 下标 表示第几行，即第几个皇后，arr[i] = val , val 表示第i+1个皇后，
 * 放在第i+1行的第val+1列
 *
 */
public class Queue {
//    定义一个max表示共有多少个皇后
    int max = 8;

    int[] array = new int[max];

    static int count = 0;
    static int judgeCount = 0;
    public static void main(String[] args) {
        // 测试，8皇后是否正确
        Queue queue = new Queue();
        queue.check(0);

        System.out.println("一共有："+count);
        System.out.println("一共冲突次数："+judgeCount);

    }

    // 编写一个方法，放置第n个皇后
    // 特别注意：check是每一次递归，都有一个for循环，因此有回溯
    private void check(int n){
        if (n == max){
            //n = 8
            // 八皇后已经放置完成
            print();
            return;
        }

        // 依次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            // 先把当前这个皇后n，放到该行的第1列
            array[n] = i;
            // 判断当放置第n个皇后到i列时是否冲突
            if (judge(n)){
                // 不冲突
                // 接着放n+1个皇后，即开始递归
                check(n+1); // 8
            }
            // 如果冲突就继续执行array[n] = i,即将第n个皇后放置在本行的后移的一个位置
        }
    }

    // 查看当我们放置第n个皇后，就去检测该皇后是否和前面已经拜访的皇后冲突
    private boolean judge(int n){
        judgeCount++;
        // n表示放第n个皇后
        for (int i = 0; i < n; i++) {
            if (array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])){
                //放在同一列 或同一斜线
                // 判断是否在同一行没有必要，因为每次n都在递增不会出现在同一行
                return false;
            }
        }
        return true;
    }

    // 写一个方法，可以将皇后摆放的位置输出
    private void print(){
        count++;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
