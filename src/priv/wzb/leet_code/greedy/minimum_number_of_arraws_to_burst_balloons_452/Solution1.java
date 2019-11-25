package priv.wzb.leet_code.greedy.minimum_number_of_arraws_to_burst_balloons_452;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/16 21:58
 * @description:
 */
public class Solution1 {
    public int findMinArrowShots(int[][] points) {
        // 对传入数据进行判定
        if (points.length == 0){
            return 0;
        }

        // 若末尾一直则按起始排序
        // 若末尾不一致则按末尾排序
        // 这里排序其实排出了一个箭可达的末尾的顺序
        Arrays.sort(points, (a,b)->{
            return a[0]-b[0];
        });

        int count =1;
        // 第一个气球的开头定位开头
        int begin = points[0][0];
        // 初始可达末尾为第一个气球的末尾
        int end = points[0][1];
        // 遍历 从第二个气球开始
        for (int i = 1; i < points.length; i++) {
            if (points[i][0]>end){
                // 如果气球的头大于（箭）可达的尾
                // 加一支箭
                count++;
                // 更新开头末尾
                begin = points[i][0];
                end = points[i][1];
            }else {
                // 可达
                // 跟新头
                begin = points[i][0];
                if (end>points[i][1]){
                    // 更新尾
                    end = points[i][1];
                }
            }
        }
        return count;
    }
}


