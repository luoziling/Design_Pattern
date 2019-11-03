package priv.wzb.multi_threaded_high_concurrent.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Satsuki
 * @time 2019/6/8 17:21
 * @description:
 */
public class SingleThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j=i;
            service.execute(()->{
                System.out.println(j+" "+ Thread.currentThread().getName());
                // 即使出错之后也不会停止运行，会创建一个新的线程来继续执行
                int x = 1/0;
            });
        }
    }
}
