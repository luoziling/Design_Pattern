package priv.wzb.jvm.oom;

import java.util.Random;

/**
 * @author Satsuki
 * @time 2020/7/5 22:17
 * @description:
 */
public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        String str="atguigu";
        while (true){
            // Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            str += str+ new Random().nextInt(1111111) + new Random().nextInt(222222222);
            str.intern();
        }
    }
}
