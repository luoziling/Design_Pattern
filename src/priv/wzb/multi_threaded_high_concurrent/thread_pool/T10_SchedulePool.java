package priv.wzb.multi_threaded_high_concurrent.thread_pool;

import org.omg.CORBA.TIMEOUT;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/6/8 16:12
 * @description:newScheduledThreadPool
 *            *创建一个线程池，可以安排命令在a之后运行
 *       *给予延迟，或定期执行。
 *       * @param corePoolSize要保留在池中的线程数，
 *       *即使他们闲着
 *       * @return新创建的预定线程池
 *       * @throws IllegalArgumentException如果{@code corePoolSize <0}
 */
public class T10_SchedulePool {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.scheduleAtFixedRate(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        },0,500, TimeUnit.MILLISECONDS);
    }
}
