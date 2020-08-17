package priv.wzb.javabook.streams;

import java.util.*;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-13 16:59
 **/

public class CollectionToStream {
    public static void main(String[] args) {
        List<Bubble> bubbles = Arrays.asList(new Bubble(1), new Bubble(2), new Bubble(3));
        System.out.println(bubbles.stream()
        .mapToInt(b->b.i)
        .sum());

        Set<String> w = new HashSet<>(Arrays.asList("It's a wonderful day for pie!".split(" ")));
        w.stream()
                .map(x->x+" ")
                .forEach(System.out::print);
        System.out.println();

        Map<String,Double> m = new HashMap<>();
        m.put("pi", 3.14159);
        m.put("e", 2.718);
        m.put("phi", 1.618);
        m.entrySet().stream()
                .map(e->e.getKey()+": " + e.getValue())
                .forEach(System.out::println);
    }
}
