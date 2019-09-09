package priv.wzb.multi_threaded_high_concurrent.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/6/8 17:21
 * @description:
 */

/**
 *创建一个根据需要创建新线程的线程池，但是
 *将重用以前构造的线程
 *可用。 这些池通常会提高性能
 *执行许多短期异步任务的程序。
 *调用{@code execute}将重用以前构建的
 *线程（如果有）。 如果没有可用的现有线程，则为新的
 *将创建线程并将其添加到池中。 有线程的
 *未使用60秒被终止并从中删除
 *缓存。 因此，一个长时间闲置的游泳池将会
 *不消耗任何资源。 请注意，池类似
 *属性但不同的细节（例如，超时参数）
 *可以使用{@link ThreadPoolExecutor}构造函数创建。
 *
 * @return新创建的线程池
 */
public class T08_CachedPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);

        for (int i = 0; i < 2; i++) {
            service.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);
        TimeUnit.SECONDS.sleep(8);
        System.out.println(service);
    }
}
