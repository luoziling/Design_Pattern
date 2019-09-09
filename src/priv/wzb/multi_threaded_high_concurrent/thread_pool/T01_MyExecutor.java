package priv.wzb.multi_threaded_high_concurrent.thread_pool;

import java.util.concurrent.Executor;

/**
 * @author Satsuki
 * @time 2019/6/8 16:18
 * @description:
 */
public class T01_MyExecutor implements Executor {
    public static void main(String[] args) {
        new T01_MyExecutor().execute(()->{
            System.out.println("Hello executor");
        });
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
