package priv.wzb.datastructure.algorithm.divideandconquer;

import org.junit.Test;

/**
 * @author Satsuki
 * @time 2019/11/4 20:55
 * @description:
 */

public class Hannoitower {
    public static void main(String[] args) {
        hanoiTower(10,'A','B','C');
    }

    // 汉诺塔移动方法
    // 使用分治算法

    /**
     * 汉诺塔问题求解
     * @param num 一共多少个盘
     * @param a 第一个塔
     * @param b 第二个塔
     * @param c 第三个塔
     */
    @Test
    public static void hanoiTower(int num,char a,char b,char c){
        // 如果只有一个盘
        if (num == 1){
            System.out.println("第1个盘从 "+a +"->"+c);
        }else {
            // n>=2情况总是看作两个盘
            //上面一个盘
            // 最下面一个盘
            // 先把最上面的所有盘A->B,移动过程中会使用到C
            String.valueOf(123);
            hanoiTower(num-1,a,c,b);
            // 把最下面的盘A->C
            System.out.println("第" + num + "个盘从 " + a + "->" + c);
            // 把b塔的所有盘移动到c 过程中借助A
            hanoiTower(num-1,b,a,c);

        }
    }
}
