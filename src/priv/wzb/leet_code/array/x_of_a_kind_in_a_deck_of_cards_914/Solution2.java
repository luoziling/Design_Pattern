package priv.wzb.leet_code.array.x_of_a_kind_in_a_deck_of_cards_914;

import java.util.ArrayList;
import java.util.List;

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
public class Solution2 {
    public boolean hasGroupsSizeX(int[] deck) {
        // 最大公约数法
        // X为所有数字的最大公约数且大于等于2就返回true
        int[] count = new int[10000];
        for (int c: deck)
            count[c]++;

        int g=-1;
        for (int i = 0; i < 10000; i++) {
            if (count[i]>0){
                if (g==-1)
                    g=count[i];
                else
                    g=gcd(g,count[i]);
            }
        }


        return g>=2;

    }

    private int gcd(int x, int y) {
        // 公约数是否为0是的话返回count[i]重新统计
        return x==0?y:gcd(y%x,x);
    }

}
