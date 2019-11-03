package priv.wzb.effective_java.HEOverride;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Satsuki
 * @time 2019/10/11 22:08
 * @description:
 */
public class Test {
    @org.junit.Test
    public void test1(){
        // 创建两个对象
        Coder c1 = new Coder("satsuki",16);
        Coder c2 = new Coder("momuji",17);
        Coder c3 = new Coder("momuji",17);

        // 构造一个HashSet将c1对象存放到其中
        Set<Coder> set = new HashSet<>();
        set.add(c1);
        set.add(c2);

        // true
        System.out.println(set.contains(c2));

        // true
        // 若不重写hashcode 则为false
        // 若重写一个hashcode返回同一个值则把哈希表降级成了链表,降低了查询速度
        System.out.println(set.contains(c3));
    }
}
