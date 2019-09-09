package priv.wzb.interview.aboutthread;

/**
 * @author Satsuki
 * @time 2019/7/3 15:19
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        Thread t=new Thread(){
            public void run(){
                pong();
            }
        };
//        t.run();
        t.start();
        System.out.println("ping");
    }
    static void pong(){
        System.out.println("pong");
    }
}
