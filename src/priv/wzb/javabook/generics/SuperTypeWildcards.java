package priv.wzb.javabook.generics;


import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-16 15:45
 * @description:
 **/
class Jonathan extends Apple{

}
public class SuperTypeWildcards {
    static void writeTo(List<? super Apple> apples){
        apples.add(new Apple());
        apples.add(new Jonathan());
//        apples.add(new Fruit());
    }

    static void writeTo1(List<? extends Apple> apples){
//        apples.add(new Apple());
//        apples.add(new Jonathan());
//        apples.add(new Fruit());
    }
}
