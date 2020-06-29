package priv.wzb.multi_threaded_high_concurrent.producter_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData{
    // 资源类
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception{
        lock.lock();


        try {
            // 1. 判断
            // 多线程，大于两个线程不用while用if判断会出现虚假唤醒
            while (number != 0){
                // 等待，不能生产
                condition.await();
            }
            // 2 干活
            number++;
            System.out.println(Thread.currentThread().getName() + " \t" + number);
            // 3 通知 唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void decrement() throws Exception{
        lock.lock();


        try {
            // 1. 判断
            while (number == 0){
                // 等待，不能生产
                condition.await();
            }
            // 2 干活
            number--;
            System.out.println(Thread.currentThread().getName() + " \t" + number);
            // 3 通知 唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public synchronized void oldIncrement() throws Exception{
        // 1. 判断
        while (number != 0){
            // 等待，不能生产
            this.wait();

        }
        // 2 干活
        number++;
        System.out.println(Thread.currentThread().getName() + " \t" + number);
        // 3 通知 唤醒
        notify();
    }
}
/**
 * @author Satsuki
 * @time 2020/5/25 16:49
 * @description:
 *  生产者消费者模式
 *  一个初始值为0的变量，两个线程对其交替操作，一个加一一个减一，来五轮
 *  线程  操作(方法)  资源类
 *  判断  干活  唤醒通知
 *  防止多线程并发状态下的虚假唤醒机制
 */
public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AAA").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BBB").start();
    }
}
