package priv.wzb.javabook.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-15 19:13
 * @description:
 **/
class Fruit{}
class Apple extends Fruit{}
public class GenericsAndCovariance {
    static int count1(List<? extends Fruit> fruits){
        // error
//        fruits.add(new Apple());
        fruits.get(0);
        for (Fruit fruit : fruits) {
            System.out.println(fruit);
        }
        return 0;
    }

    static int count2(List<Fruit> fruits){
        return 0;
    }
    public static void main(String[] args) {
        List<? extends priv.wzb.javabook.generics.Fruit> flist = new ArrayList<>();
        new ArrayList<Integer>().add(1);
//        flist.add(new Apple());
//        flist.add(new Fruit());
        List<Fruit> fruits = new ArrayList<>();
        List<Apple> apples = new ArrayList<>();
        // 用通配符接收字符串很有意义
        count1(fruits);
        count1(apples);

        count2(fruits);
        // error 定死了一个类型
//        count2(apples);
    }
}
