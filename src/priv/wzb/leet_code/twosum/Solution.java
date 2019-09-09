package priv.wzb.leet_code.twosum;

/**
 * @author Satsuki
 * @time 2019/7/17 16:10
 * @description:
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
