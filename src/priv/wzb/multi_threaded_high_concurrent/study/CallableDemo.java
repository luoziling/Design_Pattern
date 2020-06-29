package priv.wzb.multi_threaded_high_concurrent.study;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2020/6/16 15:59
 * @description:
 */



class MyThread implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println("come in");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 利用FutureTask作为中间适配器来适配不同在接口
        // 它实现了Runnable接口同时又包含Callable的内容

        // 一个多个线程共享一个FutureTask
        // 如果要调用多次记得开启多个FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        Thread t1 = new Thread(futureTask,"AAA");

        t1.start();

        new Thread(futureTask,"BBB").start();

        int result01 = 100;

        while (!futureTask.isDone()){

        }
        int result02 = futureTask.get();

        System.out.println(result01+result02);
    }
}
