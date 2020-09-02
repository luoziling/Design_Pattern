package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-31 16:18
 **/

public class TestParcel {
    public static void main(String[] args) {
        Parcel4 p = new Parcel4();
        Contents c = p.contents();
        Destination d = p.destination("Tasmania");
        // Illegal -- can't access private class:
        //- Parcel4.PContents pc = p.new PContents();
//        p.new PDestination(); -- OK
    }
}
