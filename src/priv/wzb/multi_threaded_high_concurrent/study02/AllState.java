package priv.wzb.multi_threaded_high_concurrent.study02;

/**
 * @author Satsuki
 * @time 2019/9/6 16:12
 * @description:
 */
public class AllState {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("...");
            }

        });
        //观察状态
        Thread.State state = t.getState();//NEW
        System.out.println(state);

        t.start();
        state = t.getState();//RUNNABLE
        System.out.println(state);
    }
}
