package priv.wzb.multi_threaded_high_concurrent.study02;

/**
 * @author Satsuki
 * @time 2019/9/6 16:29
 * @description:
 */
public class DaemonTest {
    public static void main(String[] args) {
        God god = new God();
        You you = new You();


        Thread t = new Thread(you,"user");

        t.setDaemon(true);
        t.start();

        new Thread(god).start();
    }
}

class You extends Thread{
    @Override
    public void run() {
        for (int i = 0; i <= 365 * 100; i++) {
            System.out.println("happy life...");
        }
        System.out.println("terminate");
    }
}

class God extends Thread{
    @Override
    public void run() {
        for (;true;){
            System.out.println("bless you...");
        }
    }
}
