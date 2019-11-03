package priv.wzb.datastructure.recursion;

/**
 * @author Satsuki
 * @time 2019/10/30 15:04
 * @description:
 */
public class RecursionTest {
    public static void main(String[] args) {
        test(4);
    }

    public static void test(int n){
        if (n>2){
            test(n-1);
        }
        System.out.println("n=" + n);
    }
}
