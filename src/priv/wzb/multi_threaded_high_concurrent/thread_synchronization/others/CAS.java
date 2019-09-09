package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.others;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Satsuki
 * @time 2019/9/7 19:44
 * @description:
 * CAS:比较并交换 compare and swap
 * 用当前的值与原来的值比较
 * 如果相等用新的值去覆盖如果不等则更新失败
 * java线程是并行的
 * sleep和wait都让线程阻塞
 * sleep不释放锁
 * wait释放锁
 * sleep模拟网络延时
 * join将线程拍成一个队列要都执行完才可以结束
 * yield从进行到就绪（让步
 * priority 线程优先级尽量提前执行但是具体还是看cpu调度
 * 用户线程和守护线程
 * 并发 多线程对同一份资源修改 synchronized + CAS + volatile
 * 同步方法
 * 同步块
 * pv模型 wait notify  synchronized 标识法和容器法
 * juc -> java.util.concurrent
 *
 */
public class CAS {

    //库存
    private static AtomicInteger stock = new AtomicInteger(5);

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    //模拟网络延时
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer left = stock.decrementAndGet();
                if(left<1){
                    System.out.println("抢完了。。。");
                    return;
                }
                System.out.println(Thread.currentThread().getName()+"抢了一件商品");
                System.out.println("left:" + left);
            }).start();
        }
    }
}
