package priv.wzb.leet_code.array.monotonic_array_896;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/28 14:00
 * @since 1.0.0
 */
public class Solution {
    public boolean isMonotonic(int[] A) {
        boolean inc = true,dec = true;
        for (int i = 0; i < A.length-1; i++) {
            if (A[i]< A[i+1]){
                dec = false;
            }
            if (A[i]> A[i+1]){
                inc = false;
            }
        }
        return inc || dec;
    }

    public static void main(String[] args) {
        int A[] = {1,1,0};
        System.out.println("new Solution().isMonotonic(A) = " + new Solution().isMonotonic(A));
    }
}
