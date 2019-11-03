package priv.wzb.multi_threaded_high_concurrent.thread_pool;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/6/8 16:26
 * @description:
 */
public class T03_Callable implements Callable<String>{
//    Callable
//    FutureTask

    public static void main(String[] args) {
        T03_Callable callable = new T03_Callable();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            System.out.println("线程启动，等待两秒");
            TimeUnit.SECONDS.sleep(2);
            System.out.println(futureTask.get());
            System.out.println(thread);
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        return "call  return";
    }
}
