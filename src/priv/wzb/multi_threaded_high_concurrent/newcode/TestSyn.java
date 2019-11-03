package priv.wzb.multi_threaded_high_concurrent.newcode;

import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/10/20 20:28
 * @description:
 */
public class TestSyn {
    public static void main(String[] args) {
        TestSyn testSyn = new TestSyn();
        testSyn.A();
//        Thread a = new Thread();

        testSyn.B();
    }

    public synchronized void A(){
        try {
            System.out.println("Thread A running...");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread A over");
    }
    public synchronized void B(){
        System.out.println("Thread B running...");
    }
}
