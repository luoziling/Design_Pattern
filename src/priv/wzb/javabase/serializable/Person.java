package priv.wzb.javabase.serializable;

import java.io.Serializable;

/**
 * @author Satsuki
 * @time 2019/10/18 20:46
 * @description:
 */
public class Person implements Serializable {
    private String name;
    private int age;

    public Person() {
        System.out.println("反序列化用到无参构造");
    }

    public Person(String name, int age) {
        System.out.println("反序列化用到有参构造");
        this.name = name;
        this.age = age;
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
