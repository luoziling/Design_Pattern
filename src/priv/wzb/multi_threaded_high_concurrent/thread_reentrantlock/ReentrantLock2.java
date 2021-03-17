package priv.wzb.multi_threaded_high_concurrent.thread_reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Satsuki
 * @time 2019/5/27 23:26
 * @description:ReentrantLock比synchronized灵活 可以使用tryLock尝试锁定
 * 尝试锁定不管锁定与否都将继续执行也可以根据返回值判定是否锁定
 * 可以制定tryLock的时间，由于tryLock(time)抛出异常，
 * 所以要注意unlock的操作必须放到finally中
 */
public class ReentrantLock2 {
    Lock lock = new ReentrantLock();
    void m1(){
        lock.lock();
        try {
            for(int i = 0;i< 10 ;i++){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    void m2(){

        boolean locked = false;
        try {
            locked = lock.tryLock(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2..." + locked);
        if (locked) lock.unlock();
//        lock.lock();
//        System.out.println("m2...");
    }

    public static void main(String[] args){
        ReentrantLock2 r2 = new ReentrantLock2();
        new Thread(r2::m1).start();
        new Thread(r2::m2).start();
    }
}
