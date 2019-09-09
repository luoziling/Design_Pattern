package priv.wzb.javabook.rtti.sweetshop;


/**
 * @author Satsuki
 * @time 2019/6/13 23:30
 * @description:
 */
public class SweetShop {
    public static void main(String[] args) {
        System.out.println("inside main");
        new Candy();
        System.out.println("After creating Candy");
        try {
            Class.forName("Gum");
//            Class.forName("Candy");

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("After Class.forName");
        new Cookie();
        System.out.println("After create Cookie");
    }
}
