package priv.wzb.multi_threaded_high_concurrent.atomic_test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Satsuki
 * @time 2020/5/20 12:15
 * @description: 使POJO改为原子型的包装类在一定程度上解决并发冲突
 */


class User{
    String username;
    int age;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        User z3 = new User("z3",22);
        User li4 = new User("li4",25);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3,li4) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3,li4) + "\t" + atomicReference.get().toString());
    }
}
