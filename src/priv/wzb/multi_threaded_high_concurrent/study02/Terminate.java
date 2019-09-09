package priv.wzb.multi_threaded_high_concurrent.study02;

/**
 * @author Satsuki
 * @time 2019/9/6 15:36
 * @description:
 * 终止线程
 * 1.正常结束
 * 2.加入标识
 */
public class Terminate implements Runnable{
    private boolean flag = true;
    int i = 0;

    private String name;

    public Terminate(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (flag){
            System.out.println(i++);
        }
    }

    public void terminate(){
        this.flag = false;
    }

    public static void main(String[] args) {
        Terminate tt = new Terminate("fubuki");
        new Thread(tt).start();

        for (int i = 0; i < 100; i++) {
            if (i==88){
                tt.terminate();
            }
            System.out.println("main-->");
        }
    }
}
