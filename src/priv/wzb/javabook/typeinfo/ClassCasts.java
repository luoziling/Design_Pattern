package priv.wzb.javabook.typeinfo;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-08 18:57
 * @description:
 **/

class Building {}
class House extends Building {}

public class ClassCasts {
    public static void main(String[] args) {
        Building b = new House();
        Class<House> houseType = House.class;
        House h = houseType.cast(b);
        h = (House)b; // ... 或者这样做.
    }
}
