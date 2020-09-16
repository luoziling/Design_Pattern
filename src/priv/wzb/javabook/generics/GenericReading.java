package priv.wzb.javabook.generics;

import java.util.Arrays;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-16 15:50
 * @description:
 **/

public class GenericReading {
    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruits = Arrays.asList(new Fruit());

    static <T> T readExact(List<T> list) {
        return list.get(0);
    }

    static void f1() {
        Apple a = readExact(apples);
        Fruit f = readExact(fruits);
        f = readExact(apples);
    }

    static class Reader<T>{
        T readExact(List<T> list){
            return list.get(0);
        }
    }

    static void f2(){
        Reader<Fruit> fruitReader = new Reader<>();
        Fruit f = fruitReader.readExact(fruits);
        //- Fruit a = fruitReader.readExact(apples);
        // error: incompatible types: List<Apple>
        // cannot be converted to List<Fruit>
    }

    static void f3(){
//        CovariantReader<Fruit>
    }

    public static void main(String[] args) {
        f1();
        f2();
        f3();
    }
}
