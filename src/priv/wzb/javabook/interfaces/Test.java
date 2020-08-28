package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 15:40
 **/

public class Test implements Concept,Bob1 {
    @Override
    public void idea1() {
        //Reference to 'a' is ambiguous, both 'Concept.a' and 'Bob1.a' match
//        System.out.println(a);
    }

    @Override
    public void idea2() {

    }
}
