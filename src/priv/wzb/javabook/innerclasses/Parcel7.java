package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-31 17:00
 **/

public class Parcel7 {
    public Contents contents(){
        return new Contents() {
            private int i =11;
            @Override
            public int value() {
                return i;
            }
        };
        // Semicolon required
    }

    public static void main(String[] args) {
        Parcel7 p = new Parcel7();
        Contents c = p.contents();
    }
}
