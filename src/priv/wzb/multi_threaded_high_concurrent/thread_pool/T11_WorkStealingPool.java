package priv.wzb.multi_threaded_high_concurrent.thread_pool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/6/8 17:23
 * @description:
 */
public class T11_WorkStealingPool {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());

        service.execute(new R(1000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));

        //由于产生的是精灵线程daemon (守护线程，后台线程），主线程不阻塞看不到输出
        System.in.read();
    }

    static class R implements Runnable{
        int time;
        R(int t){
            this.time=t;
        }
        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(time + " " + Thread.currentThread().getName());
        }
    }
}
