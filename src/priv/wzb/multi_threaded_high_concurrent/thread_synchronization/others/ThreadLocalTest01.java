package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.others;

/**
 * @author Satsuki
 * @time 2019/9/7 19:10
 * @description:
 */
public class ThreadLocalTest01 {
//    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(()->{
        return 200;
    });

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"-->" + threadLocal.get());
        threadLocal.set(99);

        System.out.println(Thread.currentThread().getName()+"-->" + threadLocal.get());
        new Thread(new MyRun()).start();
        System.out.println(Thread.currentThread().getName()+"-->" + threadLocal.get());
    }

    public static class MyRun implements Runnable{
        @Override
        public void run() {
            threadLocal.set((int)(Math.random()*99));

            System.out.println(Thread.currentThread().getName()+"-->" + threadLocal.get());

        }
    }
}
