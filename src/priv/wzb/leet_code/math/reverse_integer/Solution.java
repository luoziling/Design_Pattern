package priv.wzb.leet_code.math.reverse_integer;

/**
 * @author Satsuki
 * @time 2019/12/16 22:50
 * @description:
 */
public class Solution {
    public int reverse(int x) {
        int reverse = 0;
        while (x!=0){
            // 取最后一位
            int a = x%10;

            // 首先判定是否越界，越界置0
            if (reverse > Integer.MAX_VALUE/10 || (reverse == Integer.MAX_VALUE / 10 && a > 7)) return 0;
            if (reverse < Integer.MIN_VALUE/10 || (reverse == Integer.MIN_VALUE / 10 && a < -8)) return 0;

            // 前面的乘10相加
            reverse = reverse*10+a;

            // 原始数值除以10
            x/=10;
        }
        return reverse;
    }
}
