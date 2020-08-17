package priv.wzb.multi_threaded_high_concurrent.deadlock;


import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockA + "尝试获得："  + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockB + "尝试获得："  + lockA);

            }
        }
    }
}
/**
 * @author Satsuki
 * @time 2020/6/30 15:20
 * @description:
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA,lockB),"ThreadAAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"ThreadBBB").start();

        /**
         * linux ps -ef | grep xxxx
         * windows 下的Java运行程序 也有类似ps的查看进程命令，但是目前我们需要查看的只是Java
         *  jps = java ps           jps -l
         */
    }
}
