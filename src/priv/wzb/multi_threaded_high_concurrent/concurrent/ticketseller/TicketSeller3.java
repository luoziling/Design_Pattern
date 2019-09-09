package priv.wzb.multi_threaded_high_concurrent.concurrent.ticketseller;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/30 0:04
 * @description:
 */
public class TicketSeller3 {
    static List<String> tickets = new LinkedList<>();

    static {
        for (int i=0;i<1000;i++){
            tickets.add("票 编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (true){
                    synchronized (tickets){
                        if (tickets.size()<=0)break;

                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        System.out.println("销售了--" + tickets.remove(0));
                    }
                }
            }).start();
        }
    }
}
