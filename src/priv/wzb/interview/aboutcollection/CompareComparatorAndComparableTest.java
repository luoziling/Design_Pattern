package priv.wzb.interview.aboutcollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Satsuki
 * @time 2019/6/28 15:35
 * @description:
 */
/**
 *   (01) "Comparable"
 *   它是一个排序接口，只包含一个函数compareTo()。
 *   一个类实现了Comparable接口，就意味着“该类本身支持排序”，它可以直接通过Arrays.sort() 或 Collections.sort()进行排序。
 *   (02) "Comparator"
 *   它是一个比较器接口，包括两个函数：compare() 和 equals()。
 *   一个类实现了Comparator接口，那么它就是一个“比较器”。其它的类，可以根据该比较器去排序。
 *  *   综上所述：Comparable是内部比较器，而Comparator是外部比较器。
 *   一个类本身实现了Comparable比较器，就意味着它本身支持排序；若它本身没实现Comparable，也可以通过外部比较器Comparator进行排序。
 */

public class CompareComparatorAndComparableTest {
    public static void main(String[] args) {
        // 新建ArrayList(动态数组)
        ArrayList<Person> list = new ArrayList<>();
        // 添加对象到ArrayList中
        list.add(new Person("ccc", 20));
        list.add(new Person("AAA", 30));
        list.add(new Person("bbb", 10));
        list.add(new Person("ddd", 40));
        // 打印list的原始序列
        System.out.printf("Original  sort, list:%s\n", list);
        // 对list进行排序
        // 这里会根据“Person实现的Comparable<String>接口”进行排序，即会根据“name”进行排序
        Collections.sort(list);
        System.out.println("Name sort:" + list);
        // 通过“比较器(AscAgeComparator)”，对list进行排序
        // AscAgeComparator的排序方式是：根据“age”的升序排序
        Collections.sort(list,new AscAgeCompator());
        System.out.println("Asc(age):" + list);
        // 通过“比较器(DescAgeComparator)”，对list进行排序
        // DescAgeComparator的排序方式是：根据“age”的降序排序
        Collections.sort(list,new DescAgeComparator());
        System.out.println("Desc(age):" + list);
        // 判断两个person是否相等
        testEquals();
    }

    private static void testEquals(){
        Person p1 = new Person("eee",100);
        Person p2 = new Person("eee",100);
        if (p1.equals(p2)) {
            System.out.printf("%s EQUAL %s\n", p1, p2);
        } else {
            System.out.printf("%s NOT EQUAL %s\n", p1, p2);
        }
    }

    private static class Person implements Comparable<Person>{
        int age;
        String name;

        public Person(String name, int age) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        boolean equals(Person person){
            if(this.age == person.age && this.name==person.name){
                return true;
            }
            return false;
        }

        @Override
        public int compareTo(Person o) {
            return name.compareTo(o.name);
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private static class AscAgeCompator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getAge()-o2.getAge();
        }
    }

    private static class DescAgeComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o2.getAge()-o1.getAge();
        }
    }
}
