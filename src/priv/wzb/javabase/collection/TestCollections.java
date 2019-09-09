package priv.wzb.javabase.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/9/4 15:51
 * @description:
 */
public class TestCollections {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("w:" + i);
        }
        System.out.println(list);
        //随机排列list中元素
        Collections.shuffle(list);
        System.out.println(list);

        Collections.reverse(list);
        System.out.println(list);

        Collections.sort(list);
        System.out.println(list);

        System.out.println(Collections.binarySearch(list,"w:3"));
    }
}
