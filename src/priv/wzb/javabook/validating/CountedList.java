package priv.wzb.javabook.validating;

import java.util.ArrayList;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-02 16:33
 **/

public class CountedList extends ArrayList<String> {
    private static int counter = 0;
    private int id = counter++;

    public CountedList() {
        System.out.println("CountedList #" + id);
    }

    public int getId() {
        return id;
    }
}
