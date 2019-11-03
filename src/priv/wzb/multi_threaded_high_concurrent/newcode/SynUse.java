package priv.wzb.multi_threaded_high_concurrent.newcode;

/**
 * @author Satsuki
 * @time 2019/10/20 20:43
 * @description:
 */
public class SynUse {
    private static int j = 0;

    public static void main(String[] args) {
        SynUse synUse = new SynUse();
        Thread thread;
        AddClass addClass = synUse.new AddClass();
        LessClass lessClass = synUse.new LessClass();
        for (int i = 0; i < 2; i++) {
            new Thread(addClass).start();
            new Thread(lessClass).start();
        }

    }

    private synchronized void plus(){
        j++;
        System.out.println(Thread.currentThread().getName()+ " plus" + j);
    }

    private synchronized void less(){
        j--;
        System.out.println(Thread.currentThread().getName()+ " less" + j);
    }

    class AddClass implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                plus();
            }

        }
    }

    class LessClass implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                less();
            }
        }
    }
}
