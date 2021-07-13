package priv.wzb.javabase.times;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-21 11:10
 * @description:
 **/

public class TimeTest {

    @Test
    public void myTime(){
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDate.now());
    }

    @Test
    public void formatTest(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.toString());
        String taskAt = "2020-08-17 20:40:00";
        TemporalAccessor parse = formatter.parse(taskAt);
        String format = formatter.format(parse);
        LocalDateTime localDateTime = LocalDateTime.now();
        String format1 = formatter.format(localDateTime);
        System.out.println(format);
        System.out.println(formatter.format(localDateTime));
//        LocalDateTime localDateTime = (LocalDateTime) formatter.parse(taskAt);
//        System.out.println(formatter.format(localDateTime));
    }
}
