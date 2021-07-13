package priv.wzb.javabook.collections;

import java.util.*;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-01 14:53
 **/

public class AddingGroups {
    public static void main(String[] args) {
        Collection<Integer> collection =
                new ArrayList<>(Arrays.asList(1,2,3,4,5));
        Integer[] moreInts = {6,7,8,9,10};
        collection.addAll(Arrays.asList(moreInts));
        // 运行速度明显加快，但不能以以下方式构造集合
        Collections.addAll(collection,11,12,13,14,15);
        Collections.addAll(collection,moreInts);
        // 产生一个由数组支持的列表
        List<Integer> list = Arrays.asList(16,17,18,19,20);
        // 修改一个元素
        list.set(1,99);
        // Runtime error;底层数组无法调整大小
        list.add(21);
    }
}
