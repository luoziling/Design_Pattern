package priv.wzb.multi_threaded_high_concurrent.study;

/**
 * @author Satsuki
 * @time 2019/9/6 14:19
 * @description:
 */
public class StartRun implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("listening");
        }
    }

    public static void main(String[] args) {
        StartRun st = new StartRun();
        Thread th1 = new Thread(st);
        th1.start();
        for (int i = 0; i < 20; i++) {
            System.out.println("coding");
        }
    }
}
