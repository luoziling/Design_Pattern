package priv.wzb.multi_threaded_high_concurrent.study02;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Satsuki
 * @time 2019/9/6 16:35
 * @description:
 */
public class InfoTest {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(sdf.format(new Date()));
    }
}
