package priv.wzb.javabook.fp.recursion;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-12 17:25
 **/

public class RecursiveFibonacci {
    IntCall fib;

    RecursiveFibonacci() {
        fib = n -> n == 0 ? 0 :
                   n == 1 ? 1 :
                   fib.call(n - 1) + fib.call(n - 2);
    }

    int fibonacci(int n){
        return fib.call(n);
    }

    public static void main(String[] args) {
        RecursiveFibonacci rf = new RecursiveFibonacci();
        for (int i = 0; i < 11; i++) {
            System.out.println(rf.fibonacci(i));
        }
    }
}
