package priv.wzb.interview.test;

import org.junit.Test;

/**
 * @author Satsuki
 * @time 2019/11/11 1:04
 * @description:
 */
public class ShortTest {
    @Test
    public void shortTest(){
        short s1 = 1;
        // 报错，s1+1在运算时自动转为int，将int赋值给short会出现类型转换错误
//        s1 = s1+1;
        s1+=1;
        System.out.println("s1:" + s1);

    }

    @Test
    public void test2(){
        Integer i = 128;
        Integer j = 128;
        int a = 128;
        int b = 128;
        System.out.println(a==b);
        System.out.println(i==j);
    }

    @Test
    public void stringinternTest(){
        String s1 = "aaa";
        String s2 = "aaa";
        System.out.println(s1 == s2);
        String s3 = new String("aaa");
        String s4 = new String("aaa");
        System.out.println(s3 == s4);
        String s5 = s3.intern();
        String s6 = s3.intern();
        System.out.println(s5==s6);
    }
}
