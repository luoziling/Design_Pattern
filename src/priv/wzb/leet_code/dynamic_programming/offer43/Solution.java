package priv.wzb.leet_code.dynamic_programming.offer43;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/7/28 23:51
 * @description:
 * @since 1.0.0
 */
public class Solution {
    public static int countDigitOne(int n) {
        int res = 0;
        int flag = 0;
        for (int i = 1; i <= n; i++) {
            int x = i;
            while (x>0){
                flag = x%10;
                x/=10;
                if (flag == 1){
                    res++;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        countDigitOne(12);
    }
}
