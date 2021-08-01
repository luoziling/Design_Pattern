package priv.wzb.leet_code.sort.offer_45;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/8/1 21:33
 * @since 1.0.0
 * @description:
 * 输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: [10,2]
 * 输出: "102"
 * 示例 2:
 *
 * 输入: [3,30,34,5,9]
 * 输出: "3033459"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ba-shu-zu-pai-cheng-zui-xiao-de-shu-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public String minNumber(int[] nums) {
        // 思路：其实就是数组进行排序后输出，但是要求合并输出后的字符串最小，那么可知排序规则需要变更
        // 由原先的x>y改为 x+y>y+x
        // 例如 nums[0] = 3,nums[1] = 30  330>303 则x排在后面
        quickSort(nums,0,nums.length);
        StringBuilder sb = new StringBuilder();
        for (int num : nums) {
            sb.append(num);
        }
        return sb.toString();
    }

    public static void quickSort(int[] nums,int left,int right){
        if (left<right){
            // 找到中间枢纽，然后左右递归继续逐渐排序
            int pivot = doSort(nums,left,right);
            quickSort(nums,left,pivot-1);
            quickSort(nums,pivot+1,right);
        }
    }

    private static int doSort(int[] nums, int left, int right) {
        // 定义中间枢纽为left
        int pivot = nums[left];
        while (left<right){
            // 将小于枢纽的放到左边，大于的放到右边
            // 需要加等于，等于枢纽的要放到枢纽附近，否则等于枢纽的为排序 出错
//            while (left<right&&nums[right]>=pivot){
//                right--;
//            }
            // 比较过程变更由原先的x>y改为 x+y>y+x
            // 转为long进行比较否则超出下限
            // 这里的实际含义为 从右向左，若规律正确则继续向左，否则跳出循环 规律即 x+y>y+x，由于pivot是定义的最左侧由此得以下判断代码
            while (left<right&&Long.parseLong(String.valueOf(pivot)+String.valueOf(nums[right]))<=Long.parseLong(String.valueOf(nums[right])+String.valueOf(pivot))){
                right--;
            }
            nums[left] = nums[right];
            while (left<right&&Long.parseLong(String.valueOf(nums[left])+String.valueOf(pivot))<Long.parseLong(String.valueOf(pivot)+String.valueOf(nums[left]))){
                left++;
            }
            nums[right] = nums[left];

        }
        nums[left] = pivot;
        return left;
    }

    public static void main(String[] args) {
        int[] arr = { 3,30,34,5,9};
        System.out.println("new Solution().minNumber(arr) = " + new Solution().minNumber(arr));
    }
}
