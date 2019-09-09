package priv.wzb.multi_threaded_high_concurrent.study02;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Satsuki
 * @time 2019/9/6 15:43
 * @description:
 * sleep 模拟网络延迟
 */
public class BlockedSleep {
    public static void main(String[] args) {
//        int num=10;
//        while (true){
//            try {
//                Thread.sleep(1000);
//                System.out.println(num--);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        //倒计时

        Date endTime = new Date(System.currentTimeMillis() + 1000*10);
        long end = endTime.getTime();

        while (true){
            try {

                System.out.println(new SimpleDateFormat("mm:ss").format(endTime));
                Thread.sleep(1000);
                endTime = new Date(endTime.getTime()-1000);
                if((end-1000)>endTime.getTime()){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        Calendar cal = Calendar.getInstance();
//        System.out.println(cal.get(Calendar.YEAR));

    }
}
