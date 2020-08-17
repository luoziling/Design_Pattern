package priv.wzb.leet_code.com.remove_boxes.dynamic;

/**
 * @author Satsuki
 * @time 2019/11/9 22:29
 * @description:
 * 给出一段钢条，根据切割的不同售价也不同
 * 求出最高售价
 */
public class SteelCut1 {

    // 备忘录
    public static int cutMemo(int[] p){
        int[] r=  new int[p.length+1];
        for (int i = 0; i < p.length+1; i++) {
            r[i] = -1;
        }
        return cut(p,p.length,r);
    }

    /**
     *
     * @param p 价目表
     * @param n 现有钢条长度
     * @return 最高售价
     */
    public static int cut(int[] p,int n,int[] r){
        int q = -1;
        if (r[n]>=0){
            return r[n];
        }
        if (n==0)
            q=0;
        else {
            for (int i = 1; i <= n; i++) {
                q = Math.max(q,cut(p,n-i,r)+p[i-1]);
            }
        }
        r[n] = q;
        return q;
    }

    public static void main(String[] args) {
        int[] a = {1,5,8,9,10,17,17,20,24,30};
        System.out.println(cutMemo(a));
    }
}
