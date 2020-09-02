package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-31 16:46
 **/

public class Parcel5 {
    public Destination destination(String s){
        final class PDestination implements Destination{
            private String label;
            private PDestination(String whereTo) {
                label = whereTo;
            }
            @Override
            public String readLabel() { return label; }
        }
        return new PDestination(s);
    }

    public static void main(String[] args) {
        Parcel5 p = new Parcel5();
        Destination d = p.destination("Tasmania");
    }
}
