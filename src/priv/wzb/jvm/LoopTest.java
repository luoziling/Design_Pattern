package priv.wzb.jvm;

import org.junit.Test;

import java.util.HashMap;
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

    @Test
    public void StringTest(){
        // jdk8
        // true
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        // false
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
        System.out.println("Math.round(-11.5) = " + Math.round(-11.6));
        short s1 = 1;
        s1 = (short) (s1+1);
        HashMap<String ,String> map = new HashMap<>(2);
        map.put("1","1");
        System.out.println("map = " + map);
//        new FutureTask<>()
//        SpringApplication.run()

    }
}
