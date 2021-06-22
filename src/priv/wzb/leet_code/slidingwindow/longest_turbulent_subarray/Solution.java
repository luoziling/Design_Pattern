package priv.wzb.leet_code.slidingwindow.longest_turbulent_subarray;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/2/8 21:13
 * @since 1.0.0
 */
public class Solution {
    public int maxTurbulenceSize(int[] arr) {
        int n = arr.length;
        int ret = 1;
        int left = 0,right = 0;
        // <n-1防止溢出
        while (right< n-1){
            if (left == right){
                // 首先排除最特殊的左右相等情况，若当前与下一个数字相等则一起后移
                // 否则只是初始位置就右侧向后移
                if (arr[left] == arr[left + 1]){
                    left++;
                }
                right++;
            }else {
                // 看右侧是否能继续湍流式拓展 turbulence
                if (arr[right -1] < arr[right] && arr[right] > arr[right+1]){
                    right++;
                }else if (arr[right -1] > arr[right] && arr[right] < arr[right +1]){
                    right++;
                }else {
                    // 否则说明无法继续匹配，将left移动到right位置重新匹配
                    left = right;
                }
            }
            ret = Math.max(ret,right-left +1);
        }
        return ret;
    }
}
