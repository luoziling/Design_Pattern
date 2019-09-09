package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.others;

/**
 * @author Satsuki
 * @time 2019/9/7 19:10
 * @description:
 */
public class ThreadLocalTest03 {
//    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

//    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(()->{
//        return 200;
//    });
    private static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"-->" + threadLocal.get());
        threadLocal.set(99);

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"-->" + threadLocal.get());
        }).start();
    }


}
