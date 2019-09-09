package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.MyContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/26 17:37
 * @description:
 * 这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方法，必须要保证t2先执行，也就是让t2监听才可以
 * 可以督导输出结束并不是size=5t2推出而是t1结束t2才接收到通知而退出
 * notify之后t1必须释放锁，t2退出后也必须notify通知t1继续执行
 */
public class MyContainer2 {
    volatile List lists = new ArrayList();

    public void add(Object o){
        lists.add(o);
    }

    public int size(){
        return lists.size();
    }

    public static void main(String[] args){
        MyContainer2 c = new MyContainer2();

        final Object lock = new Object();

        new Thread(()->{
            synchronized (lock){
                System.out.println("t2启动");
                if(c.size()!=5){
                    try {
                        lock.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");
                lock.notify();
            }
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        new Thread(()->{
            System.out.println("t1启动");
            synchronized (lock){
                for(int i = 0;i<10;i++){
                    c.add(new Object());
                    System.out.println("add" + i);
                    if(c.size()==5){
//                        try {
//                            lock.notify();
//                            lock.wait();
//
//                        }catch (InterruptedException e){
//                            e.printStackTrace();
//                        }

                        lock.notify();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

        },"t1").start();


    }
}
