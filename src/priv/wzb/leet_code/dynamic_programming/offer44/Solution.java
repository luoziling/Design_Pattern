package priv.wzb.leet_code.dynamic_programming.offer44;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/7/30 0:16
 * @description:
 * @since 1.0.0
 */
public class Solution {
    public int findNthDigit(int n) {
        // 1.寻找位数
        // 初始化
        long start = 1,count = 9,digit = 1;
        while (n>count){
            n-=count;
            digit += 1;
            start*=10;
            // start*digit * 10代表所有的，*9则代表去掉之前已经统计过的
            count = 9*start*digit;
        }

        // 2.寻找数字
        long num = start + (n-1)/digit;


        // 3.寻找在数字中的位数
        return Long.toString(num).charAt((int) ((n - 1) % digit)) - '0';
    }
}
