package priv.wzb.multi_threaded_high_concurrent.thread_reentrantlock;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/28 18:08
 * @description:面试题：写一个固定容量的同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 */
public class MyContainer1<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX=10;
    private int count=0;

    public synchronized void put(T t){
        //为什么用while而不是用if?
        /*
        *原因:
        * 假设有个容器
        * 当存储满的时候用if判断的话
        * 调用this.wait线程停止
        * 如果容器内容被拿走了一个
        * 又可以往里面放东西
        * 此时唤醒线程
        * 继续调用lists.add方法然而线程是从this.wait()开始执行的
        * 如果在放入东西之前另一个线程往里面放了东西那么此时容器会饱满
        * 再往里面放就出错了。
        * 如果用while就可以再检查一遍就不会出问题了
        * */
        while (lists.size()==MAX){
            try {
                //effective java wait和while结合在一起用
                // 为了每次唤醒线程之后的再检查
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        lists.add(t);
        ++count;
        //通知消费者线程
        //如果使用notify那么就只能唤醒一个线程
        //而这个线程可能是p（生产者
        this.notifyAll();
    }

    public synchronized T get(){
        T t=null;
        while (lists.size() == 0){
            try {
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        t=lists.removeFirst();
        count--;
        this.notifyAll();
        return t;
    }

    public static void main(String[] args){
        MyContainer1<String> c = new MyContainer1<>();
        //启动消费者线程
        for(int i = 0;i<10;i++){
            new Thread(()->{
                for (int j=0;j<5;j++){
                    System.out.println(c.get());
                }
            },"c" + i).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //启动生产者线程
        for (int i = 0;i<2;i++){
            new Thread(()->{
                for(int j = 0;j<25;j++){
                    c.put(Thread.currentThread().getName() + " " + j);
                }
            },"p" + i).start();
        }
    }
}
