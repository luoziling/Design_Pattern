package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 15:58
 **/

public interface Instrument {
    // Compile-time constant:
    int VALUE = 5; // static & final

//    default void play(Note n) { // Automatically public
//        System.out.println(this + ".play() " + n);
//    }

    default void adjust() {
        System.out.println("Adjusting " + this);
    }
}
