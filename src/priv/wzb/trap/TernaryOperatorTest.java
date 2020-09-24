package priv.wzb.trap;

import org.junit.Test;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-18 09:41
 * @description:
 **/

public class TernaryOperatorTest {
    @Test
    public void test1(){
        Integer i = null;
        ternaryOPE(i);
    }

    public void ternaryOPE(Integer i) {
        //        System.out.println(1!=1 ? 0 : i);
        System.out.println(1!=1 ? new Integer(0) : i);
    }
}
