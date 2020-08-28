package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 16:10
 **/

public class Waveform {
    public static long counter;
    private final long id = counter++;

    @Override
    public String toString() {
        return "Waveform" + id;
    }
}
