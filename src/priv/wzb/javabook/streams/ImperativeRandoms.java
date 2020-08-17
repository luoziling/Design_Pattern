package priv.wzb.javabook.streams;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-13 16:44
 **/

public class ImperativeRandoms {
    public static void main(String[] args) {
        Random rand = new Random(47);
        SortedSet<Integer> rints = new TreeSet<>();
        while (rints.size() < 7){
            int r = rand.nextInt(20);
            if (r<5) continue;
            rints.add(r);
        }
        System.out.println(rints);
    }
}
