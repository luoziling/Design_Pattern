package priv.wzb.multi_threaded_high_concurrent.study02;

/**
 * @author Satsuki
 * @time 2019/9/6 15:58
 * @description:
 * yield让出cpu调度 礼让线程 暂停线程
 * 从运行变为就绪而非阻塞
 */
public class YieldDemo01 {
    public static void main(String[] args) {
        MyYield myYield = new MyYield();
        new Thread(myYield,"a").start();
        new Thread(myYield,"b").start();
    }

}

class MyYield implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "---start");
        Thread.yield();
        System.out.println(Thread.currentThread().getName() + "---end");
    }
}
