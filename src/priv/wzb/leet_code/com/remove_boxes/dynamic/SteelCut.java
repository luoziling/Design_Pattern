package priv.wzb.leet_code.com.remove_boxes.dynamic;

/**
 * @author Satsuki
 * @time 2019/11/9 22:29
 * @description:
 * 给出一段钢条，根据切割的不同售价也不同
 * 求出最高售价
 */
public class SteelCut {

    /**
     *
     * @param p 价目表
     * @param n 现有钢条长度
     * @return 最高售价
     */
    public static int cut(int[] p,int n){
        // 排除特例
        if (n == 0){
            return 0;
        }

        int q = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            q = Math.max(q,p[i-1] + cut(p,n-i));
        }
        return q;
    }

    public static void main(String[] args) {
        int[] a = {1,5,8,9,10,17,17,20,24,30};
        System.out.println(cut(a,20));
    }
}
