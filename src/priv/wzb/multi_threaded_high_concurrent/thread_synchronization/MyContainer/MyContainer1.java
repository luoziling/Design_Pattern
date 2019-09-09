package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.MyContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/26 17:21
 * @description:
 * 曾经的面试题
 * 实现一共容器，提供两个方法add,size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时
 * 县城而个日出提示并结束
 * 分析下面这个程序
 * 给lists加了volatile之后t2能够接到通知。但是，t2线程的死循环很浪费CPU
 * 如果不用死循环，该如何实现
 *到5的时候线程1通知线程2自动结束
 * 同时volatile关键字会使得程序不是很精确
 * if(c.size()==5){
 * 这句执行之后break之前如何t1线程又++又输出了怎么办
 */
public class MyContainer1 {
    volatile List lists = new ArrayList();

    public void add(Object o){
        lists.add(o);
    }

    public int size(){
        return lists.size();
    }

    public static void main(String[] args){
        MyContainer1 c = new MyContainer1();

        new Thread(()->{
            for(int i = 0;i<10;i++){
                c.add(new Object());
                System.out.println("add" + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            while (true){
                if(c.size()==5){
                    break;
                }
            }
            System.out.println("t2结束");
        },"t2").start();
    }

}
