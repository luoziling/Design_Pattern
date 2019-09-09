package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.others;

/**
 * @author Satsuki
 * @time 2019/9/7 18:42
 * @description:
 * volatile用于保证数据的同步（可见性
 */
public class VolatileTest {
    private volatile static int num=0;

    private static Integer num1 = 0;

    public static void main(String[] args) {
        new Thread(()->{
//            while (num==0){
//
//            }
            synchronized (num1){
                while (num1==0){
                    try {
                        Thread.sleep(1500);
                        Thread.yield();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();

        try {
            Thread.sleep(1000);
            num=1;
            num1 = 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
