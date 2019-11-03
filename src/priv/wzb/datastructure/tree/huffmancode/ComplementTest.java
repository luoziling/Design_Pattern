package priv.wzb.datastructure.tree.huffmancode;

/**
 * @author Satsuki
 * @time 2019/11/3 15:53
 * @description:
 */
public class ComplementTest {
    public static void main(String[] args) {
        String complement = "10101000";
        int decimal = (byte)Integer.parseInt(complement,2);
        System.out.println(decimal);
    }
}
