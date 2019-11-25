package priv.wzb.leet_code.greedy.minimum_number_of_arraws_to_burst_balloons_452;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/16 21:58
 * @description:
 */
public class Solution2 {
    public int findMinArrowShots(int[][] points) {
        // 对传入数据进行判定
        if (points.length == 0){
            return 0;
        }
        // 排序
        // 起点一致则按末尾排序，不一致则按起点排序
        Arrays.sort(points, (a,b)->{
            if (a[0] != b[0]){
                return a[0]-b[0];
            }
            return a[1]-b[1];
        });
        int count =1;
        // 初始可达末尾为第一个气球的末尾
        int end = points[0][1];
        // 遍历 从第二个气球开始
        for (int i = 1; i < points.length; i++) {
            // 如果气球头节点大于当前可达的末尾
            if(points[i][0]>end){
                // 多加一支箭
                count++;
                // 跟新可达最远末尾
                end = points[i][1];
            }else {
                // 跟新末尾
                end = Math.min(end,points[i][1]);
            }
            // 如果有一个气球的范围的开头大于end就是不在第一个气球的可达范围之内
//            if (points[i][0]>end){
//                // 就多加一名弓箭手更新可达末尾（可以射穿气球的 范围
//                end = points[i][1];
//                count++;
//            }
        }
        return count;
    }
}


