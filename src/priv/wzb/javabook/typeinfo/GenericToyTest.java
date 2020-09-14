package priv.wzb.javabook.typeinfo;

import priv.wzb.javabook.rtti.toy.FancyToy;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-08 18:55
 * @description:
 **/

public class GenericToyTest {
    public static void main(String[] args) throws Exception {
        Class<FancyToy> ftClass = FancyToy.class;
        // Produces exact type:
        FancyToy fancyToy = ftClass.newInstance();
        Class<? super FancyToy> up =
                ftClass.getSuperclass();
        // This won't compile:
        // Class<Toy> up2 = ftClass.getSuperclass();
        // Only produces Object:
        Object obj = up.newInstance();
    }
}
