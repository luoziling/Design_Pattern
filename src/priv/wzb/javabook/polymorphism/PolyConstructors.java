package priv.wzb.javabook.polymorphism;

import java.util.LinkedList;
import java.util.List;

public class PolyConstructors {
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        // offer方法无法调用
//        (LinkedList)list.offer(1);

        new RoundGlyph(5);
    }
}
