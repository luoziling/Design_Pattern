package priv.wzb.javabook.rtti.shape;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author Satsuki
 * @time 2019/6/13 23:21
 * @description:
 */
public class Shapes {
    public static void main(String[] args) {
        Vector s = new Vector();
        s.addElement(new Circle());
        s.addElement(new Square());
        s.addElement(new Triangle());
        Enumeration e = s.elements();
        while (e.hasMoreElements()){
            ((Shape)e.nextElement()).draw();
        }
    }
}
