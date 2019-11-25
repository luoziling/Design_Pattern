package priv.wzb.interview.test;

/**
 * @author Satsuki
 * @time 2019/11/12 17:13
 * @description:
 */
public class test11 {
    public static void main(String[] args) {
//        System.out.print(B.c);
        System.out.println(Test2.a);
    }
}
class A {

    static {

        System.out.print("A");

    }

}

class Test2{





//    public static final String a=new String("JD");

    public static final String a="JD";

    static {

        System.out.print("OK");

    }



}



class B extends A{

    static {

        System.out.print("B");

    }

    public final static String c = "C";

}