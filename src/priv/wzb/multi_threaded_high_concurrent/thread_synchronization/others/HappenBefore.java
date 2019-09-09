package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.others;

/**
 * @author Satsuki
 * @time 2019/9/7 18:32
 * @description:
 * 指令重排：代码执行顺序与预期不一致
 * 目的：提高性能
 */
public class HappenBefore {
    private static int a = 0;
    private static boolean flag = false;
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            a=0;
            flag = false;

            //线程1 更改

            Thread t1 = new Thread(()->{
                a = 1;
                flag = true;
            });
            //线程2 读取
            Thread t2 = new Thread(()->{
                if(flag){
                    a*=1;
                }

                if(a==0){
                    System.out.println("happen before:a=" + a);
                }
            });

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
