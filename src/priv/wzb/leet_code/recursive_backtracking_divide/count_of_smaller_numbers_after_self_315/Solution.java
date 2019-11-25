package priv.wzb.leet_code.recursive_backtracking_divide.count_of_smaller_numbers_after_self_315;

import priv.wzb.interview.test.P;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/20 21:24
 * @description: 给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
 * <p>
 * 示例:
 * <p>
 * 输入: [5,2,6,1]
 * 输出: [2,1,1,0]
 * 解释:
 * 5 的右侧有 2 个更小的元素 (2 和 1).
 * 2 的右侧仅有 1 个更小的元素 (1).
 * 6 的右侧有 1 个更小的元素 (1).
 * 1 的右侧有 0 个更小的元素.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-of-smaller-numbers-after-self
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    private int[] temp;
    private int[] counter;
    private int[] indexes;

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int len = nums.length;
        if (len == 0) {
            return res;
        }
        // 初始化
        temp = new int[len];
        counter = new int[len];
        indexes = new int[len];
        // 记录数字对应的下标
        for (int i = 0; i < len; i++) {
            indexes[i] = i;
        }
        mergeAndCountSmaller(nums, 0, len - 1);
        for (int i = 0; i < len; i++) {
            res.add(counter[i]);
        }
        return res;
    }

    /**
     * 针对数组nums指定的区间[l,r]进行归并排序，在排序过程中完成统计
     *
     * @param nums
     * @param l
     * @param r
     */
    private void mergeAndCountSmaller(int[] nums, int l, int r) {
        if (l == r) {
            // 数组只有一个元素不比较不统计
            return;
        }
        int mid = l + (r - l) / 2;
        mergeAndCountSmaller(nums, l, mid);
        mergeAndCountSmaller(nums, mid + 1, r);
        // 归并排序优化
        // 如果索引数组有序就没必要继续计算
        if (nums[indexes[mid]] > nums[indexes[mid + 1]]) {
            mergeOfTwoSortedArrAndCountSmaller(nums, l, mid, r);
        }


    }

    /**
     * 回到本题，本题让我们求 “在一个数组的某个元素的右边，
     * 比自己小的元素的个数”，因此，我们就 应该在 “前有序数组”
     * 的元素出列的时候，数一数 “后有序数组” 已经出列了多少元素，
     * 因为这些已经出列的元素都比当前出列的元素要小（或者等于）。
     * <p>
     * 在归并排序过程中
     * [l,mid]
     * [mind+1,r]肯定是有序
     *
     * @param nums
     * @param l
     * @param mid
     * @param r
     */
    private void mergeOfTwoSortedArrAndCountSmaller(int[] nums, int l, int mid, int r) {
        // 不改变原始数组
        for (int i = l; i <= r; i++) {
            temp[i] = indexes[i];
        }
        int i = l;
        int j = mid + 1;
        // 左边出列的时候，计数
        // 更改索引数组模拟排序
        for (int k = l; k <= r; k++) {
            // 左边用完
            // 直接放右边即可
            if (i > mid) {
                indexes[k] = temp[j];
                j++;
            } else if (j > r) {
                // 右边用完放左边即可
                indexes[k] = temp[i];
                i++;
                // 此时j用完了
                // 右边用完就代表到了最后此时取值就应该取r-mid
                // 之前的数据和后面的区间长度构成逆序
                counter[indexes[k]] += (r - mid);
            } else if (nums[temp[i]] <= nums[temp[j]]) {
                indexes[k] = temp[i];
                i++;
                counter[indexes[k]] += (j - mid - 1);
            } else {
                // 构成逆序
//                counter[indexes[k]]>nums[indexes[j]];
                indexes[k] = temp[j];
                j++;
            }
        }
    }
}
