package priv.wzb.javabook.collections;

import java.util.HashMap;
import java.util.Random;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-01 15:56
 **/

public class Statistics {
    public static void main(String[] args) {
        Random rand = new Random(47);
        HashMap<Integer, Integer> m = new HashMap<>(4);

        for (int i = 0; i < 10000; i++) {
            // Produce a number between 0 and 20:
            int r = rand.nextInt(20);
            Integer freq = m.get(r);
            m.put(r,freq==null?1:freq+1);
        }
        System.out.println(m);

    }
}
