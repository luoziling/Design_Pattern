package priv.wzb.leet_code.twosum;

/**
 * @author Satsuki
 * @time 2019/7/17 16:10
 * @description:
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int firstNum,secNum;
        for (int i = 0; i < nums.length; i++) {
            firstNum=nums[i];
            for (int j = 0; j < nums.length; j++) {
                secNum=nums[j];
                if((firstNum+secNum==target)&&i!=j){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int a[] = {3,2,4};
        int target=6;
        Solution solution = new Solution();
        int s[]=solution.twoSum(a,target);
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]);
        }
    }
}
