package priv.wzb.leet_code.dynamic_programm30ing.range_sum_query_immutable_303;

/**
 * NumArray
 *
 * @author yuzuki
 * @date 2021/3/1 21:16
 * @since 1.0.0
 */
public class NumArray {
    // 缓存，内置容器存储定量结果，方便计算，空间换时间
    int[] sums;
    public NumArray(int[] nums) {
        sums = new int[nums.length+1];
        sums[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            sums[i+1] = sums[i] + nums[i];
        }
    }

    public int sumRange(int i, int j) {
        // 由于sums从0开始所以要+1统计j由于也统计i所以原本i应该-1但从0开始就不用了
        return sums[j+1]-sums[i];
    }
}
