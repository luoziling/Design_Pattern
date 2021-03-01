package priv.wzb.leet_code.slidingwindow.minimum_number_of_k_consecutive_bit_flips;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/18 20:57
 * @since 1.0.0
 */
public class Solution {
    public int minKBitFlips(int[] A, int K) {
        // 差分数组
        int n = A.length;
        // 统计反转次数的差分数组
        int[] diff = new int[n+1];
        // 反转次数与累加值
        int ans = 0,revCnt = 0;
        // 遍历
        for (int i = 0; i < n; i++) {
            revCnt += diff[i];
            // 若当前总和为0且到达尽头 返回-1
            if ((A[i] + revCnt) % 2 == 0){
                if (i + K > n){
                    return -1;
                }
                // 旋转次数增加
                ++ans;
                // 累计自增
                ++revCnt;
                // 超出滑动窗口的部分需要自减来反向证明窗口开头的自增
                --diff[i + K];
            }
        }
        return ans;
    }
}
