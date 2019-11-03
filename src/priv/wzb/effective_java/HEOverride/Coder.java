package priv.wzb.effective_java.HEOverride;

import java.util.Objects;

/**
 * @author Satsuki
 * @time 2019/10/11 22:07
 * @description:
 * 一个对象重写hashcode与equals方法
 */
public class Coder {
    private String name;
    private int age;

    public Coder() {
    }

    public Coder(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coder coder = (Coder) o;
        return age == coder.age &&
                name.equals(coder.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
