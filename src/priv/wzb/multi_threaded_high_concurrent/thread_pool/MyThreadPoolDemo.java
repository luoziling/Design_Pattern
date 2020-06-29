package priv.wzb.multi_threaded_high_concurrent.thread_pool;

import java.util.concurrent.*;

/**
 * @author Satsuki
 * @time 2020/6/16 16:53
 * @description:
 * 第四种获得使用Java多线程的方式，线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());

        // CPU密集型，不断计算，那么配置为CPU核心数+1
        // IO密集型，不断读取硬盘，例如数据库。就配置为CPU核心数/1-阻塞系数(0.8~0.9)
        // IO例如4核CPU4/1-0.9=40个线程数
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        // AbortPolicy 超出直接报异常 RejectedExecutionException
        // CallerRunsPolicy 调用者运行机制，回退给调用者去运行
        // DiscardOldestPolicy 抛弃等待最久的,只能执行8个所以抛弃了两个
        // DiscardPolicy 直接抛弃多的两个

        try {
            // 最多承载max+阻塞队列数 = 5+3 = 8
            // 这边开9立即出错
            for (int i = 1;i<=10;i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }

    private static void threadPoolInit() {
        System.out.println(Runtime.getRuntime().availableProcessors());

        // 一池固定5线程
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 一池一线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();


        // 看情况开启和关闭线程
        ExecutorService threadPool = Executors.newCachedThreadPool();

        // 模拟十个用户来办理业务
        try {
            for (int i = 1;i<=10;i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                // 暂停一会
                TimeUnit.MILLISECONDS.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
