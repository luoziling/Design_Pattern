package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.pvmodel;

/**
 * @author Satsuki
 * @time 2019/9/7 17:14
 * @description:
 * 协作模型：生产者消费者实现方式一：管程法
 * 借助缓冲区
 */
public class PVTest01 {
    public static void main(String[] args) {
        WzbContainer container = new WzbContainer();
        new Productor(container).start();
        new Consumer(container).start();
    }
}

//生产者

class Productor extends Thread{
    WzbContainer container;

    public Productor(WzbContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        //生产
        for (int i = 0; i < 100; i++) {
            System.out.println("生产--第"+ i + "个馒头");
            container.push(new Steamedbun(i));
        }
    }
}
//消费者
class Consumer extends Thread{
    WzbContainer container;

    public Consumer(WzbContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        //消费
        for (int i = 0; i < 100; i++) {
            System.out.println("消费--第"+ container.pop().id + "个馒头");
        }
    }
}
//缓冲区
class WzbContainer{
    Steamedbun[] buns = new Steamedbun[10];
    int count=0;//计数器

    //存储 生产
    public synchronized void push(Steamedbun bun){
        //不能生产只有等待
        if(count == buns.length){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //何时生产 容器存在空间
        buns[count] = bun;
        count++;
        //存在数据可以消费
        this.notifyAll();
    }
    //获取 消费
    public synchronized Steamedbun pop(){
        //何时消费 容器中是否存在数据
        //存在数据可以消费无数据等待
        if(count==0){
            try {
                this.wait();//线程阻塞 生产者通知消费解除
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //存在数据可以消费
        count--;
        Steamedbun bun = buns[count];
        this.notifyAll();//存在空间，可以唤醒对方生产
        return bun;
    }
}
//数据(馒头)
class Steamedbun{
    int id;

    public Steamedbun(int id) {
        this.id = id;
    }
}