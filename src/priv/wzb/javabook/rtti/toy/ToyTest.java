package priv.wzb.javabook.rtti.toy;

/**
 * @author Satsuki
 * @time 2019/9/12 16:50
 * @description:
 */
public class ToyTest {
    public static void main(String[] args) {
        Class c=  null;
        try {
            c = Class.forName("priv.wzb.javabook.rtti.toy.FancyToy");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        printInfo(c);
        Class[] faces = c.getInterfaces();
        for (int i = 0; i < faces.length; i++) {
            printInfo(faces[i]);
            Class cy = c.getSuperclass();
            Object o = null;
            try {
                //Requires default constructor
                o=cy.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            printInfo(o.getClass());
        }
    }

    static void printInfo(Class cc){
        System.out.println(
                "Class name:" + cc.getName()+
                "is interface?[" +
                cc.isInterface()+"]"
        );
    }
}
