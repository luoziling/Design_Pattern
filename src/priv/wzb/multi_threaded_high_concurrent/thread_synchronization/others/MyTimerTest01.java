package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.others;

import priv.wzb.multi_threaded_high_concurrent.thread_synchronization.T;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/9/7 17:58
 * @description:
 * notify notifyAll and wait in Object
 * sleep yield in Thread
 *
 * 任务调度：Timer
 */
public class MyTimerTest01 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        //执行安排
        //参数说明： TimerTask ,执行延迟（多久后执行，执行间隔：多久执行一次
//        timer.schedule(new MyTask(),1000);//执行任务一次
//        timer.schedule(new MyTask(),1000,200);//执行任务多次
        Calendar cal = new GregorianCalendar(2099,12,31,21,53,54);
        timer.schedule(new MyTask(),cal.getTime(),200);//指定时间



    }
}

//任务类
class MyTask extends TimerTask{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("放空大脑休息一会");
        }
        System.out.println("end");
    }
}

