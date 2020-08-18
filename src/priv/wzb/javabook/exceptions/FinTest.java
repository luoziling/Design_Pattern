package priv.wzb.javabook.exceptions;

/**
 * @author Satsuki
 * @time 2020/8/17 22:12
 * @description:
 */
public class FinTest {
    void f(){

        try {
            System.out.println("111");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\"finally\" = " + "finally");
        }
    }
    public static void main(String[] args) {
        new FinTest().f();
    }
}
