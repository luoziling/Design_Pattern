package priv.wzb.leet_code.list.LFU_Cache_460;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2020/4/5 16:00
 * @description:
 */
public class LFUCache {
    // 容器
    int[] space;
    // 考虑用链表做放入记录
    LinkedList<Integer> list = new LinkedList<>();
    // hash的参数
    static int CAPACITY;
    // 键值对用于维护key映射后的位置
    HashMap<Integer,Integer> keyMap = new HashMap<>();

    // 维护每个内容的使用次数
    int[] usage;
    public LFUCache(int capacity) {
        CAPACITY = capacity;
        // 初始化容器
        space = new int[capacity];
        usage = new int[capacity];
        // 初始化为-1
        for (int i = 0; i < capacity; i++) {
            space[i] = -1;
            // 初始化使用次数
            usage[i] = 0;
        }

    }

    public int get(int key) {
        if (null!=keyMap.get(key)){
            // 增加使用次数
            usage[keyMap.get(key)]++;
            return space[keyMap.get(key)];
        }else {
            return -1;
        }

    }

    public void put(int key, int value) {
        // 通过hash存取
        // 映射到0-CAPACITY方位内
        int mKey = key%CAPACITY;

        if (space[mKey] == -1){
            // 如果正好有空就存放
            space[mKey] = value;
            keyMap.put(key,mKey);
        }else {
            // 有值解决冲突
            // 解决hash冲突
            // 从后一个开始找
            int key1 = (mKey+1)%CAPACITY;
            // 循环一圈看看是否有空
            while (key1!=mKey && space[key1]!=-1){
                key1 = (key1+1)%CAPACITY;
            }
            if (key1==mKey){
                // 满了需要清楚最近最少使用
                // 记录最少使用的
                // 最近最少使用
                int minIndex = usage[0];
                for (int i = 1; i < CAPACITY; i++) {
                    if (usage[i]<minIndex){
                        minIndex = i;
                    }
                }
                // 移除，添加新内容
                // 想办法把之前的记录删除
                int removeKey = -1;
                for(Integer iKey : keyMap.keySet()){
                    if (keyMap.get(iKey) == minIndex){
                        removeKey = iKey;
                    }
                }
                keyMap.remove(removeKey);
                space[minIndex] = value;

                keyMap.put(key,minIndex);
            }else {
                // 找到位置插入
                space[key1] = value;
                keyMap.put(key,key1);
            }
        }

    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache( 2 /* capacity (缓存容量) */ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // 返回 1
        cache.put(3, 3);    // 去除 key 2
        cache.get(2);       // 返回 -1 (未找到key 2)
        cache.get(3);       // 返回 3
        cache.put(4, 4);    // 去除 key 1
        cache.get(1);       // 返回 -1 (未找到 key 1)
        cache.get(3);       // 返回 3
        cache.get(4);       // 返回 4

    }
}
