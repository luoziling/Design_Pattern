package priv.wzb.leet_code.dynamic_programming.dungeon_game_174;

/**
 * @author Satsuki
 * @time 2019/12/5 22:43
 * @description:
 * 一些恶魔抓住了公主（P）并将她关在了地下城的右下角。地下城是由 M x N 个房间组成的二维网格。我们英勇的骑士（K）最初被安置在左上角的房间里，他必须穿过地下城并通过对抗恶魔来拯救公主。
 *
 * 骑士的初始健康点数为一个正整数。如果他的健康点数在某一时刻降至 0 或以下，他会立即死亡。
 *
 * 有些房间由恶魔守卫，因此骑士在进入这些房间时会失去健康点数（若房间里的值为负整数，则表示骑士将损失健康点数）；其他房间要么是空的（房间里的值为 0），要么包含增加骑士健康点数的魔法球（若房间里的值为正整数，则表示骑士将增加健康点数）。
 *
 * 为了尽快到达公主，骑士决定每次只向右或向下移动一步。
 *
 *  
 *
 * 编写一个函数来计算确保骑士能够拯救到公主所需的最低初始健康点数。
 *
 * 例如，考虑到如下布局的地下城，如果骑士遵循最佳路径 右 -> 右 -> 下 -> 下，则骑士的初始健康点数至少为 7。
 *
 * -2 (K)	-3	3
 * -5	-10	1
 * 10	30	-5 (P)
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/dungeon-game
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 回溯法使用DFS或者BFS进行全部的搜索取一个最小值
 */
public class Solution {
    // 全局变量去保存最小值
    int minHealth = Integer.MAX_VALUE;
    public int calculateMinimumHP(int[][] dungeon) {
        // 初始的时候给加一点血，addHealth和health都是1
        calculateMinimumHPHelper(0,0,1,1,dungeon);
        return minHealth;
    }

    /**
     *
     * @param x 代表哪一列
     * @param y 代表哪一行
     * @param health 代表当前生命值
     * @param addHealth 代表已经增加的生命值
     * @param dungeon 地下城
     */
    private void calculateMinimumHPHelper(int x,int y,int health,int addHealth,int[][] dungeon){
        // 家伙是那个当前位置的奖励或者惩罚
        health = health + dungeon[y][x];
        // 此时是否需要加血，加血的花将health加到1
        if (health<=0){
            addHealth = addHealth+Math.abs(health)+1;
            return;
        }

        // 是否到达了终点
        if (x == dungeon[0].length - 1 && y == dungeon.length - 1) {
            minHealth = Math.min(addHealth, minHealth);
            return;
        }

        // 是否加过血
        //是否加过血
        if (health <= 0) {
            //加过血的话，health 就变为 1
            if (x < dungeon[0].length - 1) {
                calculateMinimumHPHelper(x + 1, y, 1, addHealth, dungeon);
            }
            if (y < dungeon.length - 1) {
                calculateMinimumHPHelper(x, y + 1, 1, addHealth, dungeon);
            }
        } else {
            //没加过血的话，health 就是当前的 health
            if (x < dungeon[0].length - 1) {
                calculateMinimumHPHelper(x + 1, y, health, addHealth, dungeon);
            }
            if (y < dungeon.length - 1) {
                calculateMinimumHPHelper(x, y + 1, health, addHealth, dungeon);
            }
        }

    }
}
