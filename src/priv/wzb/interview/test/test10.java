package priv.wzb.interview.test;

import org.junit.Test;

/**
 * @author Satsuki
 * @time 2019/11/11 14:30
 * @description:
 */
public class test10 {

    // 斐波那契
    private static int func(int n){
        if (n<2)
            return n;
        return func(n-1)+func(n-2);
    }
    @Test
    public void test1(){
        System.out.println(func(2));
    }

    @Test
    public void test2(){
        System.out.println(S.abc);
    }

    @Test
    public void test3(){
        System.out.println(mTest());
    }

    @Test
    public void test4(){
        byte a = 127;
        byte b = 127;
        a+=b;
        System.out.println(a);
    }

    @Test
    public void test5(){
        int i, n = 0;

        float x = 1, y1 = (float) (2.1 / 1.9), y2 = (float) (1.9 / 2.1);



        for ( i = 1; i < 22; i++)

            x = x * y1;



        while ( x != 1.0 )

        {

            x = x * y2; n++;

        }


        System.out.printf( "%d\n", n);
    }

    public static int mTest(){
        int temp = 1;

        try {

            System.out.println(temp);

            return ++temp;

        } catch (Exception e) {

            System.out.println(temp);

            return ++temp;

        } finally {

            ++temp;

            System.out.println(temp);

        }
    }

    @Test
    public void test06(){
        int m = 016, n = 18;
        System.out.println(m>n?m:n);
    }
}
