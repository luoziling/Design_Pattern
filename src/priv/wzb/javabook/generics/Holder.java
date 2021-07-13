package priv.wzb.javabook.generics;

import java.util.Objects;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-15 19:24
 * @description:
 **/

public class Holder<T> {
    private T value;

    public Holder() {
    }

    public Holder(T value) {
        this.value = value;
    }

    public T get(){
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Holder && Objects.equals(value,((Holder)obj).value);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public static void main(String[] args) {
        Holder<Apple> apple = new Holder<>(new Apple());
        Apple d = apple.get();
//        apple.set(d);
        // Holder<Fruit> fruit = apple; // Cannot upcast
        Holder<? extends Fruit> fruit = apple; // OK
        Fruit p = fruit.get();
        d = (Apple) fruit.get();
        try {
//            Orange c = (Orange) fruit.get(); // No warning
        } catch (Exception e) {
            System.out.println(e);
        }
        // fruit.set(new Apple()); // Cannot call set()
        // fruit.set(new Fruit()); // Cannot call set()
        System.out.println(fruit.equals(d)); // OK
    }
}
