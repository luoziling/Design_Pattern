package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.pvmodel;

/**
 * @author Satsuki
 * @time 2019/9/7 17:14
 * @description:
 * 协作模型：生产者消费者实现方式一：信号灯法
 * 借助标识位
 */
public class PVTest02 {
    public static void main(String[] args) {
        Tv tv = new Tv();
        new Player(tv).start();
        new Watcher(tv).start();
    }
}
//生产者 演员
class Player extends Thread{
    Tv tv;

    public Player(Tv tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if(i%2==0){
                this.tv.play("奇葩说");
            }else {
                this.tv.play("太污了，呵瓶立白洗洗嘴");
            }
        }
    }
}

//消费者 观众
class Watcher extends Thread{
    Tv tv;

    public Watcher(Tv tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            tv.watch();
        }
    }
}

//同一个资源 电视
class Tv{
    String voice;
    //信号灯
    //T 表示演员表演观众等待
    //F 表示观众观看演员等待
    boolean flag = true;

    //表演
    public synchronized void play(String voice){
        //演员等待
        if (!flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        this.voice = voice;
        //表演
        System.out.println("表演了" + voice);
        this.notifyAll();
        this.flag=!this.flag;
    }

    //观看
    public synchronized void watch(){
        //观众等待
        if (flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //观看
        System.out.println("听到了" + voice);
        //唤醒
        this.notifyAll();
        //切换标识
        this.flag=!this.flag;
    }
}