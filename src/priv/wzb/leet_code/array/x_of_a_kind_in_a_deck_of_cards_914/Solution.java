package priv.wzb.leet_code.array.x_of_a_kind_in_a_deck_of_cards_914;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2020/3/27 20:39
 * @description:
 * 给定一副牌，每张牌上都写着一个整数。
 *
 * 此时，你需要选定一个数字 X，使我们可以将整副牌按下述规则分成 1 组或更多组：
 *
 * 每组都有 X 张牌。
 * 组内所有的牌上都写着相同的整数。
 * 仅当你可选的 X >= 2 时返回 true。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：[1,2,3,4,4,3,2,1]
 * 输出：true
 * 解释：可行的分组是 [1,1]，[2,2]，[3,3]，[4,4]
 * 示例 2：
 *
 * 输入：[1,1,1,2,2,2,3,3]
 * 输出：false
 * 解释：没有满足要求的分组。
 * 示例 3：
 *
 * 输入：[1]
 * 输出：false
 * 解释：没有满足要求的分组。
 * 示例 4：
 *
 * 输入：[1,1]
 * 输出：true
 * 解释：可行的分组是 [1,1]
 * 示例 5：
 *
 * 输入：[1,1,2,2,2,2]
 * 输出：true
 * 解释：可行的分组是 [1,1]，[2,2]，[2,2]
 *
 * 提示：
 *
 * 1 <= deck.length <= 10000
 * 0 <= deck[i] < 10000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/x-of-a-kind-in-a-deck-of-cards
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public boolean hasGroupsSizeX(int[] deck) {
        // 统计有多少个不同的数字并统计这些数字的个数与最多数字的个数
        Map<Integer,Integer> numMap = new HashMap<>();
        for (int i = 0; i < deck.length; i++) {
            if (numMap.containsKey(deck[i])){
                // 存在该数字
                numMap.put(deck[i],numMap.get(deck[i])+1);
            }else {
                // 不存在
                numMap.put(deck[i],1);
            }
        }

        boolean flag;

        // 遍历看看能否达成条件
        int X;
        for (Map.Entry<Integer,Integer> entry: numMap.entrySet()){
            X = entry.getValue();
            // 初始化为true然后去验证如果仍为true则返回
            flag = true;
            // X必须>=2
            if (X<2){
                return false;
            }
            // 遍历所有可能的情况
            for (int i = 2; i <= X; i++) {
                flag = true;
                for (Map.Entry<Integer,Integer> interior: numMap.entrySet()){
                    // 如果无法按照X分组则置为false
                    if (interior.getValue()%i != 0){
                        flag = false;
                    }
                }
                if (flag){
                    return true;
                }
            }
//            for (Map.Entry<Integer,Integer> interior: numMap.entrySet()){
//                // 如果无法按照X分组则置为false
//                if (interior.getValue()%X != 0){
//                    flag = false;
//                }
//            }
//            if (flag){
//                return true;
//            }
        }
        return false;

//        // 分为i种不同的可能
//        for (int i = 2; i <= deck.length; i++) {
//            for (int j = 0; j < deck.length; j++) {
//                // 分组
//
//            }
//        }
    }
}
