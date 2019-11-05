package priv.wzb.datastructure.algorithm.greedy;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/5 20:01
 * @description:
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        // 创建广播电台，放入map
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

        HashSet<String> allAreas = new HashSet<>();
        for (Map.Entry<String, HashSet<String>> entry: broadcasts.entrySet()){
            HashSet<String> value = entry.getValue();
            Iterator<String> iterator = value.iterator();
            while (iterator.hasNext()){
                String next = iterator.next();
                if (!allAreas.contains(next)){
                    allAreas.add(next);
                }
            }
        }
        System.out.println(allAreas.toString());

        // 创建一个ArrayList存放选择的电台集合
        ArrayList<String> selects = new ArrayList<>();

        // 定义一个临时集合
        // 在遍历过程中存放遍历过程中的电台覆盖的地区
        // 和当前还没有覆盖的地区的交集
        HashSet<String> tempSet = new HashSet<>();

        // 定义一个maxKey,保存在依次遍历过程中，能够覆盖最多未覆盖的地区对应的电台的key
        // 如果maxKey,不为null，则会加入到selects
        // 如果allAreas不为0说明未覆盖所有地区
        String maxKey = null;
        while (allAreas.size()!=0){
            // maxKey置空
            maxKey = null;


            // 遍历broadcasts取出对应的key
            for(String key: broadcasts.keySet()){
                // 清理tempSet
                tempSet.clear();

                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                // 求出tempSet和AllAreas的交集

                tempSet.retainAll(allAreas);
                // 如果当前这个集合包含的未覆盖地区数量大于之前遍历时候找到的集合
                // 重置包含最多未覆盖地区的集合
                // tempSet.size()>broadcasts.get(maxKey).size() 体现贪心算法，每次都选最好的
                if (tempSet.size()>0 &&
                        (maxKey == null || (tempSet.size()>broadcasts.get(maxKey).size()))){
                    maxKey = key;
                }
            }

            if (maxKey!=null){
                selects.add(maxKey);
                // 取出已覆盖的地区
                allAreas.removeAll(broadcasts.get(maxKey));
            }
        }

        System.out.println("得到的选择结果：" + selects);
    }
}
