package priv.wzb.javabook.reuse;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-24 16:08
 **/

public class Instrument {
    public void play(){}

    static void tune(Instrument i){
        i.play();
    }
}
