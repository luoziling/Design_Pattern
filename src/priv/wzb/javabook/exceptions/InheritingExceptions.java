package priv.wzb.javabook.exceptions;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-14 16:25
 **/

public class InheritingExceptions{
    public void f() throws SimpleException{
        System.out.println("Throw SimpleException from f()");
        throw new SimpleException();
    }

    public static void main(String[] args) {
        InheritingExceptions sed =
                new InheritingExceptions();
        try {
            sed.f();
        } catch (SimpleException e) {
            e.printStackTrace();
            System.out.println("Caught it!");
        }
    }
}
