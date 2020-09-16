package priv.wzb.javabook.generics;

import priv.wzb.multi_threaded_high_concurrent.thread_synchronization.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-15 17:49
 * @description:
 **/

public class GenericVarargs {
    @SafeVarargs
    public static <T>List<T> makeList(T... args){
        List<T> result = new ArrayList<>();
        for (T arg : args) {
            result.add(arg);
        }
        return result;
    }

    public static List<T> a(){
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        List<String> ls = makeList("A");
        System.out.println(ls);
        ls = makeList("A0","B","c");
        System.out.println(ls);
        ls = makeList("ABCDEFFHIJKLMNOPQRSTUVWXYZ".split(""));
        System.out.println(ls);
    }
}
