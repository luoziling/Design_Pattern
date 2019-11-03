package priv.wzb.multi_threaded_high_concurrent.study;

import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/9/6 14:52
 * @description:
 */
public class Web12306 implements Runnable {
    private int tickedNums = 99;
    @Override
    public void run() {
        while (true){
            if(tickedNums<0){
                break;
            }

            //模拟网络延迟
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(tickedNums>=0){
                synchronized (this){
                    while (tickedNums>=0){
                        System.out.println(Thread.currentThread().getName()+":" + tickedNums--);
                        break;
                    }


                }
            }
        }
    }

    public static void main(String[] args) {
        //一份资源
        Web12306 web = new Web12306();

        //多个代理
        new Thread(web,"th1").start();
        new Thread(web,"th2").start();
        new Thread(web,"th3").start();
    }
}
