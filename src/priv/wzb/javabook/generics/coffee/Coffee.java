package priv.wzb.javabook.generics.coffee;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-14 19:43
 * @description:
 **/

public class Coffee {
    private static long counter = 0;
    private final long id = counter++;

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
}
