package priv.wzb.interview.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/10/13 22:04
 * @description:
 */
class Fruit {}
class Apple extends Fruit {}
class Jonathan extends Apple {}
class Orange extends Fruit {}
public class CovariantArrays {
    public static void main(String[] args) {
        Object a;
        String b;
        // 上界
        List<? extends Fruit> flistTop = new ArrayList<Apple>();
        flistTop.add(null);
        // add Fruit对象会报错
//        flistTop.add(new Fruit());
//        flistTop.add(new Apple());
//        flistTop.add(new Jonathan());
        Fruit fruit = flistTop.get(0);

        //下界
        List<? super Apple> flistBottem = new ArrayList<Apple>();
        flistBottem.add(new Apple());
        flistBottem.add(new Jonathan());
//        flistBottem.add(new Object());
//        flistBottem.add(new Fruit());
        //get Apple对象会报错
        //Apple apple = flistBottem.get(0);
//        Object o = flistBottem.get(0);


    }
}
