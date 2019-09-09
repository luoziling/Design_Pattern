package priv.wzb.javabase.treeset;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Satsuki
 * @time 2019/9/4 14:02
 * @description:
 */
public class TestTreeSet {
    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();

        set.add(300);
        set.add(600);
        set.add(500);

        for (Integer m: set){
            System.out.println(m);
        }
    }
}
