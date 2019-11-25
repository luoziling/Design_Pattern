package priv.wzb.leet_code.qianxin.remove_boxes;

import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/11/9 21:25
 * @description:
 */
public class Solution {
    public int removeBoxes(int[] boxes) {
        // 积分
        int integral = 0;

        //保存已统计过的颜色
        LinkedList<Integer> list = new LinkedList<>();

        // 颜色
        int color;
        // 同颜色盒子数量
        int boxNum;
        for (int i = 0; i < boxes.length; i++) {
            color = boxes[i];
            boxNum = 1;
            // 未统计过这种颜色的盒子才要统计
            if (!list.contains(color)){
                for (int j = i+1; j < boxes.length; j++) {
                    // 颜色相同
                    if (boxes[j] == color){
                        // 同颜色盒子数量增加
                        boxNum++;
                    }
                }
                // 一轮结束
                // 获得积分
                integral += boxNum*boxNum;
            }
            list.add(color);

        }

        return integral;
    }

    public static void main(String[] args) {
//        int a[] = {1, 3, 2, 2, 2, 3, 4, 3, 1};
        int a[] = {1,2,1,2,1};
        int i = new Solution().removeBoxes(a);
        System.out.println(i);
    }
}
