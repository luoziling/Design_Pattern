package priv.wzb.javabook.enums;

import java.util.EnumMap;
import java.util.Map;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-21 16:11
 **/import static priv.wzb.javabook.enums.AlarmPoints.*;

interface Command { void action(); }
public class EnuMaps {
    public static void main(String[] args) {
        EnumMap<AlarmPoints,Command> em = new EnumMap<AlarmPoints, Command>(AlarmPoints.class);
        em.put(KITCHEN,
                ()->{
                    System.out.println("Kitchen fire!");
                });
        em.put(BATHROOM,
                () -> System.out.println("Bathroom alert!"));
        for(Map.Entry<AlarmPoints,Command> e:
                em.entrySet()) {
            System.out.print(e.getKey() + ": ");
            e.getValue().action();
        }
        try {
            em.get(UTILITY).action();
        } catch (Exception e) {
            System.out.println("Excepted:" + e);
        }
    }
}
