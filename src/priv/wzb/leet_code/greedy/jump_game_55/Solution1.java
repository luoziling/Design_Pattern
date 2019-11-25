package priv.wzb.leet_code.greedy.jump_game_55;

/**
 * @author Satsuki
 * @time 2019/11/14 22:24
 * @description:
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 判断你是否能够到达最后一个位置。
 *
 * 示例 1:
 *
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
 * 示例 2:
 *
 * 输入: [3,2,1,0,4]
 * 输出: false
 * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/jump-game
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 挨个跳，如果可以条到尾部说明成功
 */
public class Solution1 {
    public boolean canJump(int[] nums) {
        // 记录从每个点可达的最远位置
        int[]  index = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            index[i] = i+nums[i];
        }

        // 下标，一个一个位置的跳
        int jump = 0;
        // 记录最远可达的位置
        int maxIndex = index[0];
        // 当中间一环出现了问题无法继续后跳或者到了尾部就说明跳跃结束
        // 例如[3,2,1,0,4]你怎么努力最多跳到下标为3的位置
        // 但是到了此位置无法向后跳
        // maxIndex停滞在了3但是jump在继续向后遍历此时发现jump超过了可达位置则跳出即可
        while (jump<index.length&&jump<=maxIndex){
            if (maxIndex<index[jump]){
                maxIndex = index[jump];
            }
            jump++;
        }
        // jump到达了数组尾（jump是不可能越界的但是maxIndex则是可能越界的
        if (jump == index.length){
            return true;
        }
        return false;
    }
}
