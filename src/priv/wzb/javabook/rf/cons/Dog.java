package priv.wzb.javabook.rf.cons;

/**
 * @author Satsuki
 * @time 2020/8/12 20:48
 * @description:
 */
public class Dog {
    String name;
    int age = -1;
    Dog() { name = "stray"; }
    Dog(String nm) { name = nm; }
    Dog(String nm, int yrs) { name = nm; age = yrs; }
}
