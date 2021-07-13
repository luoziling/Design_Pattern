package priv.wzb.javabook.typeinfo;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-08 18:09
 * @description:
 **/
class Cookie{
    static {
        System.out.println("Loading Cookie");
    }
}

class Gum{
    static {
        System.out.println("Loading Gum");
    }
}
class Candy{
    static {
        System.out.println("Loading Candy");
    }
}
public class SweetShop {
    public static void main(String[] args) {
        System.out.println("inside main");
        new Candy();
        System.out.println("After creating candy");
        try {
            Class.forName("priv.wzb.javabook.typeinfo.Gum");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("After Class.forName(\"Gum\")");
        new Cookie();
        System.out.println("After creating Cookie");
    }
}
