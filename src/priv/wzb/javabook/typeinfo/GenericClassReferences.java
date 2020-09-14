package priv.wzb.javabook.typeinfo;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-08 18:35
 * @description:
 **/

public class GenericClassReferences {
    public static void main(String[] args) {
        Class intClass = int.class;
        Class<Integer> genericIntClass = int.class;
        genericIntClass = Integer.class;
        intClass = double.class;
        // 出错 因为限定了Integer的泛型
//        genericIntClass = double.class;

//        Class<Number> geenericNumberClass = int.class;

        Class<?> intClass1 = int.class;
        intClass1 = double.class;

        Class<? extends Number> bounded = int.class;
        bounded = double.class;
        bounded = Number.class;
        // Or anything else derived from Number.
    }
}
