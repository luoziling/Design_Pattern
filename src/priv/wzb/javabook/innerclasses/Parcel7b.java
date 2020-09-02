package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-31 17:04
 **/

public class Parcel7b {
    class MyContents implements Contents{
        private int i = 11;
        @Override
        public int value() { return i; }
    }

    public Contents contents() {
        return new MyContents();
    }

    public static void main(String[] args) {
        Parcel7b p = new Parcel7b();
        Contents c = p.contents();
    }


}
