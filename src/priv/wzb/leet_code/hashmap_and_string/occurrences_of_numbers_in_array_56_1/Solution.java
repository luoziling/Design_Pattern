package priv.wzb.leet_code.hashmap_and_string.occurrences_of_numbers_in_array_56_1;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2020/4/28 17:40
 * @description:
 * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [4,1,4,6]
 * 输出：[1,6] 或 [6,1]
 * 示例 2：
 *
 * 输入：nums = [1,2,10,4,1,4,3,3]
 * 输出：[2,10] 或 [10,2]
 *  
 *
 * 限制：
 *
 * 2 <= nums <= 10000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int[] singleNumbers(int[] nums) {
        //所有数字的异或
        // 先对所有数字进行一次异或，得到两个出现一次的数字的异或值。
        int k =0;
        for(int num : nums){
            k ^= num;
        }
        // 获得k中最低位的1
        // 在异或结果中找到任意为 1 的位。
        int mask = 1;
        // 有一个数字1它可以不断乘以1变为10 100 1000
        // 因为与运算的性质，与0必定为0所以相当于在遍历每一位指导找到为1的
        while ((k&mask)==0){
            // <<相当于乘以2
            mask<<=1;
        }
        int a = 0;
        int b = 0;
        // 根据这一位对所有的数字进行分组。
        for(int num : nums){
            if ((num&mask)==0){
                // 在每个组内进行异或操作，得到两个数字。
                a^=num;
            }else {
                b^=num;
            }
        }
        return new int[]{a,b};
    }
}
