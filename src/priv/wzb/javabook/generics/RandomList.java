package priv.wzb.javabook.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-14 19:32
 * @description:
 **/

public class RandomList<T> extends ArrayList<T> {
    private Random rand = new Random(47);

    public T select(){
        return get(rand.nextInt(size()));
    }

    public static void main(String[] args) {
        RandomList<String> rs = new RandomList<>();
        Arrays.stream("The quick brown fox jumped over the lazy brown dog".split(" "))
                .forEach(rs::add);
        IntStream.range(0,11).forEach(i->{
            System.out.println(rs.select()+" ");
        });
    }
}
