package priv.wzb.multi_threaded_high_concurrent.study02;

/**
 * @author Satsuki
 * @time 2019/9/6 16:06
 * @description:
 * join合并线程，插队线程
 */
public class BlockedJoin01 {
    public static void main(String[] args) {
        Thread t=  new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("lambda..." + i);
            }
        });
        t.start();

        for (int i = 0; i < 100; i++) {
            if(i==20){
                try {
                    t.join();//插队 阻塞主线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("main..." + i);
        }
    }
}
