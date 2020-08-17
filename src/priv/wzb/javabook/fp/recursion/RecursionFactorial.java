package priv.wzb.javabook.fp.recursion;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-12 17:23
 **/

public class RecursionFactorial {
//    IntCall fact;
    static IntCall fact;

    public static void main(String[] args) {
        fact = n->n==0?1:n*fact.call(n-1);
        for (int i = 0; i < 10; i++) {
            System.out.println(fact.call(i));
        }
    }
}
