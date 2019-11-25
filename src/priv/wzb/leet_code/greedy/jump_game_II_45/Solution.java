package priv.wzb.leet_code.greedy.jump_game_II_45;

/**
 * @author Satsuki
 * @time 2019/11/16 21:34
 * @description:
 */
public class Solution {
    public int jump(int[] nums) {
        // 小于二不用跳跃，返回零
        if (nums.length<2){
            return 0;
        }
        // 当前可达的最远位置
        int current_max_index = nums[0];
        // 遍历各个位置过程中，可达到的最远位置
        int pre_max_max_index = nums[0];
        // 初始值必须是1因为即使只有两个格子也是需要进行一次跳跃的
        int jumpMin = 1;
        for (int i = 1; i < nums.length; i++) {
            // 无法向前移动了，才进行跳跃
            if (current_max_index<i){
                // 增加跳跃次数
                jumpMin++;
                // 更新最远可达距离
                current_max_index = pre_max_max_index;
            }
            // 若i最远位置大于当前可达最远距离则更新
            if (pre_max_max_index<nums[i]+i){
                pre_max_max_index = nums[i]+i;
            }
        }

        return jumpMin;

    }

}
