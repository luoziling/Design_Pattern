package priv.wzb.javabook.validating;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-02 16:38
 **/

public class CountedListTest {
    private CountedList list;

    @BeforeAll
    static void beforeAllMsg(){
        System.out.println(">>> Starting CountedListTest");
    }

    @AfterAll
    static void afterAllMsg(){
        System.out.println(">>> Finished CountedListTest");
    }

    @BeforeEach
    public void initialize() {
        list = new CountedList();
        System.out.println("Set up for " + list.getId());
        for(int i = 0; i < 3; i++)
            list.add(Integer.toString(i));
    }

    @AfterEach
    public void cleanup() {
        System.out.println("Cleaning up " + list.getId());
    }

    @Test
    public void insert(){
        System.out.println("Running testInsert()");
        assertEquals(list.size(),3);
        list.add(1,"Insert");
        assertEquals(list.size(),4);
        assertEquals(list.get(1),"Insert");
    }

    @Test
    public void replace() {
        System.out.println("Running testReplace()");
        assertEquals(list.size(), 3);
        list.set(1, "Replace");
        assertEquals(list.size(), 3);
        assertEquals(list.get(1), "Replace");
    }

    // A helper method to simplify the code. As
    // long as it's not annotated with @Test, it will
    // not be automatically executed by JUnit.
    private void compare(List<String> lst, String[] strs) {
        assertArrayEquals(lst.toArray(new String[0]), strs);
    }

    @Test
    public void order() {
        System.out.println("Running testOrder()");
        compare(list, new String[] { "0", "1", "2" });
    }

    @Test
    public void remove() {
        System.out.println("Running testRemove()");
        assertEquals(list.size(), 3);
        list.remove(1);
        assertEquals(list.size(), 2);
        compare(list, new String[] { "0", "2" });
    }

    @Test
    public void addAll() {
        System.out.println("Running testAddAll()");
        list.addAll(Arrays.asList(new String[] {
                "An", "African", "Swallow"}));
        assertEquals(list.size(), 6);
        compare(list, new String[] { "0", "1", "2",
                "An", "African", "Swallow" });
    }
}
