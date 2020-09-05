package priv.wzb.javabook.polymorphism;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-05 14:26
 **/

public class MTest1 {
    @Test
    public void casting(){
        // 向上转型
        // 用父类引用接子类对象，没问题
        List<Integer> list = new LinkedList<>();
        // 看似调用父类的add方法实则运行时具体找到子类对象调用子类方法的具体实现
        // 也没问题
        list.add(1);
        // 报错，父类中没有getLast方法
//        list.getLast
        // 父类向下转型
        // 由于指向子类对象所以没问题
        Integer first = ((LinkedList<Integer>) list).getFirst();
        System.out.println(first);
    }
}
