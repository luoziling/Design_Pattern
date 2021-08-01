package priv.wzb.leet_code.dynamic_programming.offer43;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/7/28 23:51
 * @description:
 * dp
 * 定义 dp就是0-n中1出现的次数，每次只需判断当前1出现的次数即可
 * 方案
 * 逐个遍历
 * 数字拼接后遍历
 * @since 1.0.0
 */
public class Solution1 {
    public static int countDigitOne(int n) {
        int res = 0;
        // 统计每一位数字置1时 其他位所受影响
        // 位 初始个位
        int digit = 1;
        // 初始低位
        int low = 0;
        // 初始位
        int cur = n%10;
        // 高位
        int high = n/(digit*10);
        // 根据当前位大小提供计算公式
        while (high != 0 || cur != 0){
            if (cur == 0){
                // 只受高位影响
                // 小于1 则需从高位借位
                res+=high*digit;
            }
            if (cur == 1){
                // 等于1则同时受高位和低位影响，同时无法借位给低位
                res+= high*digit+low+1;
            }
            if (cur >1){
                // 大于1则受到高位和低位影响同时可以借位 导致高位可以重复统计一次
                res += (high+1) * digit;
            }
            low += digit*cur;
            cur = high%10;
            high /=10;
            digit*=10;
        }
        return res;

    }

}
