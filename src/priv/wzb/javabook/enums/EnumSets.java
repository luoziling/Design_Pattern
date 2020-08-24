package priv.wzb.javabook.enums;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-21 15:54
 **/
import java.util.EnumSet;

import static priv.wzb.javabook.enums.AlarmPoints.*;
public class EnumSets {
    public static void main(String[] args) {
        // Empty
        EnumSet<AlarmPoints> points =
                EnumSet.noneOf(AlarmPoints.class);
        points.add(BATHROOM);
        System.out.println(points);
        points.addAll(
                EnumSet.of(STAIR1, STAIR2, KITCHEN));
        System.out.println(points);
        points = EnumSet.allOf(AlarmPoints.class);
        points.removeAll(
                EnumSet.of(STAIR1, STAIR2, KITCHEN));
        System.out.println(points);
        points.removeAll(
                EnumSet.range(OFFICE1, OFFICE4));
        System.out.println(points);
        points = EnumSet.complementOf(points);
        System.out.println(points);
    }
}
