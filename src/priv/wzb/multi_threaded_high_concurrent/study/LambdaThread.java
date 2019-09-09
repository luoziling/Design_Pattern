package priv.wzb.multi_threaded_high_concurrent.study;

/**
 * @author Satsuki
 * @time 2019/9/6 15:13
 * @description:
 */
public class LambdaThread {
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("hello");
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hell2");
            }
        }).start();
    }
}
