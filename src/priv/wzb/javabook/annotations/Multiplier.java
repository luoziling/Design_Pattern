package priv.wzb.javabook.annotations;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-17 10:45
 * @description:
 **/
@ExtractInterface(interfaceName = "IMultiplier")
public class Multiplier {
    public boolean flag = false;
    private int n = 0;

    public int multiply(int x,int y) {
        int total = 0;
        for (int i = 0; i < x; i++) {
            total = add(total,y);
        }
        return total;
    }
    public int fortySeven() { return 47; }
    private int add(int x, int y) {
        return x + y;
    }
    public double timesTen(double arg) {
        return arg * 10;
    }

    public static void main(String[] args) {
        Multiplier m = new Multiplier();
        System.out.println(
                "11 * 16 = " + m.multiply(11, 16));
    }
}
