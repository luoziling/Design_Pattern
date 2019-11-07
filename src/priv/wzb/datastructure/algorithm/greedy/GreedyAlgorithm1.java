package priv.wzb.datastructure.algorithm.greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/11/7 16:48
 * @description:
 */
public class GreedyAlgorithm1 {
    public static void main(String[] args) {
        Map<String, HashSet<String>> broadcasts = new HashMap<>();
        // 将各个电台放入broadcasts
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");
        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");
        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");
        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");
        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        broadcasts.put("k1",hashSet1);
        broadcasts.put("k2",hashSet2);
        broadcasts.put("k3",hashSet3);
        broadcasts.put("k4",hashSet4);
        broadcasts.put("k5",hashSet5);

        HashSet<String> areas = new HashSet<>();
        for(Map.Entry<String,HashSet<String>> entry : broadcasts.entrySet()){
            HashSet<String> value = entry.getValue();
            for(String s : value){
                areas.add(s);
            }
        }
        System.out.println("areas:" + areas);
        HashSet<String> rightBroadcasts = new HashSet<>();
        String broadcast;
        HashSet<String> hashSet = new HashSet<>();

        while (areas.size()>0){
            // 每次都需要重置
            broadcast = null;
            for(Map.Entry<String,HashSet<String>> entry: broadcasts.entrySet()){
                // 贪心算法，每次都判断未覆盖地区最多的广播电台加入
                HashSet<String> value = entry.getValue();
                // 取交集
                value.retainAll(areas);
                if (broadcast!= null){
                    hashSet = broadcasts.get(broadcast);
                    hashSet.retainAll(areas);
                }
                // 找寻可以覆盖未覆盖地区最多的广播
                if (value.size()>0 && (broadcast == null|| value.size()>hashSet.size())){
                    broadcast = entry.getKey();
                }
            }
            // 加入并删除已覆盖区域
            rightBroadcasts.add(broadcast);
            areas.removeAll(broadcasts.get(broadcast));
        }
        System.out.println("挑选的广播电台:" + rightBroadcasts.toString());

    }
}
