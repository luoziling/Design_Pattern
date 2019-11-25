package priv.wzb.leet_code.greedy.minimum_number_of_arraws_to_burst_balloons_452;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Satsuki
 * @time 2019/11/16 21:58
 * @description:
 */
public class Solution {
    public int findMinArrowShots(int[][] points) {
        // 对传入数据进行判定
        if (points.length == 0){
            return 0;
        }
        // 排序
//        Arrays.sort(points, new Comparator<int[]>() {
//            @Override
//            public int compare(int[] o1, int[] o2) {
//                if (o1[1]!=o2[1]){
//                    return o1[1]-o2[1];
//                }
//                return o1[0]-o2[0];
//            }
//        });
        // 若末尾一致则按起始排序
        // 该排序尽量使得较短范围的气球在前
        // 若末尾不一致则按末尾排序
        // 该排序使末尾较远的气球排在后面
        // 这里排序其实排出了一个箭可达的末尾的顺序
        // 若末尾一致则按照气球起始进行排序
        Arrays.sort(points, (a,b)->{
            if (a[1]!=b[1]){
                return a[1]-b[1];
            }
            return a[0]-b[0];
        });
        int count =1;
        // 初始可达末尾为第一个气球的末尾
        int end = points[0][1];
        // 遍历 从第二个气球开始
        for (int i = 1; i < points.length; i++) {
            // 如果有一个气球的范围的开头大于end就是不在第一个气球的可达范围之内
            if (points[i][0]>end){
                // 就多加一名弓箭手更新可达末尾（可以射穿气球的 范围
                end = points[i][1];
                count++;
            }
        }
        return count;
    }
}


