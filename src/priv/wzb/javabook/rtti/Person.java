package priv.wzb.javabook.rtti;

import java.util.Optional;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-11 16:14
 * @description:
 **/

public class Person {
    public final Optional<String> first;
    public final Optional<String> last;
    public final Optional<String> address;
    // etc.
    public final Boolean empty;

    public Person(String first, String last, String address) {
        // ofNullable如果为空返回一个null的封装对象，否则返回源对象
        this.first = Optional.ofNullable(first);
        this.last = Optional.ofNullable(last);
        this.address = Optional.ofNullable(address);
        // isPresent表示是否不为null !=null
        empty = !this.first.isPresent()
                && !this.last.isPresent()
                && !this.address.isPresent();
    }

    Person(String first, String last) {
        this(first, last, null);
    }

    Person(String last) {
        this(null, last, null);
    }

    Person() {
        this(null, null, null);
    }
    @Override
    public String toString() {
        if (empty)
            return "<Empty>";
        // orElse用于判断是否为空，为空返回替换的值，否则就返回原值
        return (first.orElse("") +
                " " + last.orElse("") +
                " " + address.orElse("")).trim();
    }

    public static void main(String[] args) {
        System.out.println(new Person());
        System.out.println(new Person("Smith"));
        System.out.println(new Person("Bob", "Smith"));
        System.out.println(new Person("Bob", "Smith",
                "11 Degree Lane, Frostbite Falls, MN"));
    }
}
