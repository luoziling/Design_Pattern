package priv.wzb.jvm;

import java.util.Random;
import java.util.UUID;

/**
 * @author Satsuki
 * @time 2020/7/8 14:47
 * @description:
 */
public class LoopTest {
    public static void main(String[] args) {
        Random random = new Random();
        while (true){
            System.out.println(""+random.nextInt() + "*******" + UUID.randomUUID() );
        }
    }
}
