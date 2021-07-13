package priv.wzb.javabook.enums;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-21 15:02
 **/

// enums/EnumClass.java
// Capabilities of the Enum clas

/**
 *
 */
enum Shrubbery { GROUND, CRAWLING, HANGING }
public class EnumClass {
    public static void main(String[] args) {
        for(Shrubbery s : Shrubbery.values()) {
            System.out.println(
                    s + " ordinal: " + s.ordinal());
            System.out.print(
                    s.compareTo(Shrubbery.CRAWLING) + " ");
            System.out.print(
                    s.equals(Shrubbery.CRAWLING) + " ");
            System.out.println(s == Shrubbery.CRAWLING);
            System.out.println(s.getDeclaringClass());
            System.out.println(s.name());
            System.out.println("********************");
        }
// Produce an enum value from a String name:
        for(String s :
                "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery shrub =
                    Enum.valueOf(Shrubbery.class, s);
            System.out.println(shrub);
        }
    }
}

