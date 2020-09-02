package priv.wzb.javabook.collections;

import java.util.Iterator;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-01 16:23
 **/

public class IterableClass implements Iterable<String> {
    protected String[] words = ("And that is how " +
            "we know the Earth to be BANANA-SHAPED."
    ).split(" ");
    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index<words.length;
            }

            @Override
            public String next() {
                return words[index++];
            }
        };
    }

    public static void main(String[] args) {
        for (String s : new IterableClass()) {
            System.out.print(s + " ");
        }
    }
}
